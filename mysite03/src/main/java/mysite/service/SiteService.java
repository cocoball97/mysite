package mysite.service;

import org.springframework.stereotype.Service;

import mysite.repository.SiteRepository;
import mysite.vo.SiteVo;

@Service
public class SiteService {
	private SiteRepository siteRepository;
	
	public SiteService(SiteRepository siteRepository) {
		this.siteRepository = siteRepository;
	}
	
	public SiteVo getSite() {
		System.out.println(("출력================="+siteRepository.find().getProfile()));
		return siteRepository.find();
	}
	
	public void updateSite(SiteVo siteVo) {
		siteRepository.update(siteVo);
	}
}
