package com.eric.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.eric.exception.base.BaseException;
import com.eric.repository.IAreaDao;
import com.eric.repository.ITranscityDao;
import com.eric.repository.ITransportDao;
import com.eric.repository.ITransfeeDao;
import com.eric.repository.entity.*;
import com.eric.service.TransportService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class TransportServiceImpl  implements TransportService {

	@Resource
	private  ITransportDao mTransportMapper;

	@Resource
	private ITransfeeDao mTransfeeMapper;

	@Resource
	private  ITranscityDao mTranscityMapper;
	@Resource
	private IAreaDao mAreaDao;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertTransportAndTransfee(Transport transport) {
		// 插入运费模板
		mTransportMapper.insert(transport);
		// 插入所有的运费项和城市
		insertTransfeeAndTranscity(transport);

		// 插入所有的指定包邮条件项和城市
		if (transport.getHasFreeCondition() == 1) {
			insertTransfeeFreeAndTranscityFree(transport);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(cacheNames = "TransportAndAllItems", key = "#transport.transportId")
	public void updateTransportAndTransfee(Transport transport) {
		Transport dbTransport = getTransportAndAllItems(transport.getTransportId());

		// 删除所有的运费项
		mTransfeeMapper.deleteByTransportId(transport.getTransportId());
		// 删除所有的指定包邮条件项
		mTransfeeMapper.deleteTransfeeFreesByTransportId(transport.getTransportId());

		if (dbTransport.getTransfees() != null) {
			List<Long> transfeeIds = dbTransport.getTransfees().stream().map(Transfee::getTransfeeId).collect(Collectors.toList());
			mTranscityMapper.deleteBatchByTransfeeIds(transfeeIds);
		}
		List<Long> transfeeFreeIds = dbTransport.getTransfeeFrees().stream().map(TransfeeFree::getTransfeeFreeId).collect(Collectors.toList());

		// 删除所有运费项包含的城市
		if(CollectionUtil.isNotEmpty(transfeeFreeIds)) {
			// 删除所有指定包邮条件项包含的城市
			mTranscityMapper.deleteBatchByTransfeeFreeIds(transfeeFreeIds);
		}

		// 更新运费模板
		mTransportMapper.updateById(transport);

		// 插入所有的运费项和城市
		insertTransfeeAndTranscity(transport);
		// 插入所有的指定包邮条件项和城市
		if (transport.getHasFreeCondition() == 1) {
			insertTransfeeFreeAndTranscityFree(transport);
		}
	}


	private void insertTransfeeFreeAndTranscityFree(Transport transport) {
		Long transportId = transport.getTransportId();
		List<TransfeeFree> transfeeFrees = transport.getTransfeeFrees();
		for (TransfeeFree transfeeFree : transfeeFrees) {
			transfeeFree.setTransportId(transportId);
		}
		// 批量插入指定包邮条件项 并返回指定包邮条件项 id，供下面循环使用
		mTransfeeMapper.insertTransfeeFrees(transfeeFrees);

		List<TranscityFree> transcityFrees = new ArrayList<>();
		for (TransfeeFree transfeeFree : transfeeFrees) {
			List<Area> cityList = transfeeFree.getFreeCityList();
			if (CollectionUtil.isEmpty(cityList)) {
				throw new BaseException("请选择指定包邮城市");
			}
			// 当地址不为空时
			for (Area area : cityList) {
				TranscityFree transcityParam = new TranscityFree();
				transcityParam.setTransfeeFreeId(transfeeFree.getTransfeeFreeId());
				transcityParam.setFreeCityId(area.getAreaId());
				transcityFrees.add(transcityParam);
			}
		}

		// 批量插入指定包邮条件项中的城市
		if (CollectionUtil.isNotEmpty(transcityFrees)) {
			mTranscityMapper.insertTranscityFrees(transcityFrees);
		}
	}

	private void insertTransfeeAndTranscity(Transport transport) {
		Long transportId = transport.getTransportId();
		List<Transfee> transfees = transport.getTransfees();
		for (Transfee transfee : transfees) {
			transfee.setTransportId(transportId);
		}
		// 批量插入运费项 并返回运费项id，供下面循环使用
		mTransfeeMapper.insertTransfees(transfees);

		List<Transcity> transcities = new ArrayList<>();
		for (Transfee transfee : transfees) {
			List<Area> cityList = transfee.getCityList();
			if (CollectionUtil.isEmpty(cityList)) {
				continue;
			}
			// 当地址不为空时
			for (Area area : cityList) {
				Transcity transcityParam = new Transcity();
				transcityParam.setTransfeeId(transfee.getTransfeeId());
				transcityParam.setCityId(area.getAreaId());
				transcities.add(transcityParam);
			}
		}

		// 批量插入运费项中的城市
		if (CollectionUtil.isNotEmpty(transcities)) {
			mTranscityMapper.insertTranscities(transcities);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteTransportAndTransfeeAndTranscity(Long[] ids) {


		for (Long id : ids) {
			Transport dbTransport = getTransportAndAllItems(id);
			if (dbTransport.getTransfees()!=null ) {
				List<Long> transfeeIds = dbTransport.getTransfees().stream().map(Transfee::getTransfeeId).collect(Collectors.toList());
				// 删除所有运费项包含的城市
				mTranscityMapper.deleteBatchByTransfeeIds(transfeeIds);
			}
			// 删除所有的运费项
			mTransfeeMapper.deleteByTransportId(id);
		}
		// 删除运费模板
		mTransportMapper.deleteTransports(ids);
	}


	@Override
//	@Cacheable(cacheNames = "TransportAndAllItems", key = "#transportId")
	public Transport getTransportAndAllItems(Long transportId) {
		Transport transport = mTransportMapper.selectById(transportId);
		if (transport == null) {
			return null;
		}
		//	查运费项
		List<Transfee> transfees = mTransfeeMapper.selectByTransportId(transportId);
		// 运费项相关城市
		for (Transfee transfee : transfees) {
			List<Transcity> transcities = mTranscityMapper.selectByTransfeeId(transfee.getTransfeeId());
			List<Area> cityList = new ArrayList<>();
			for (Transcity transcity : transcities) {
				cityList.add(mAreaDao.getById(transcity.getCityId()));
			}
			transfee.setCityList(cityList);
		}
		transport.setTransfees(transfees);
		// 查包邮
		List<TransfeeFree> transfeeFrees = mTransportMapper.getTransfeeFreeAndTranscityFreeByTransportId(transportId);
		transport.setTransfeeFrees(transfeeFrees);
		return transport;
	}

	@Override
//	@CacheEvict(cacheNames = "TransportAndAllItems", key = "#transportId")
	public void removeTransportAndAllItemsCache(Long transportId) {

	}

	@Override
	public List<Transport> list(Long shopId) {
		return mTransportMapper.list(shopId);
	}
}
