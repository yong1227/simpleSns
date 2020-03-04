package com.simple.sns.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.simple.sns.domain.FollowVO;
import com.simple.sns.domain.ResponseResult;
import com.simple.sns.domain.TokenVO;
import com.simple.sns.repository.FollowDAO;

@Service
public class FollowService {

	private static final Logger logger = LoggerFactory.getLogger(FollowService.class);

	@Autowired
	FollowDAO followDAO;

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	@Autowired
	FeedService feedService;

	public ResponseResult insertFollow(String accesstoken, FollowVO followVO) {

		TokenVO tokenVO = userService.findTokenByToken(accesstoken);

		Long userId = tokenVO.getUserId();
		Long followeeId = followVO.getFolloweeId();

		followVO.setFollowerId(userId);
		followVO.setFolloweeId(followeeId);

		int countResult = followDAO.findCountFollowByFolloweeIdAndFollowerId(followVO);

		if (countResult == 0) {
			followDAO.insertFollow(followVO);
		}

		// insertFollowMySelf
//		followVO.setFolloweeId(userId);
//		followVO.setFollowerId(userId);

//		countResult = followDAO.findCountFollowByFolloweeIdAndFollowerId(followVO);

//		if (countResult == 0) {
//			followDAO.insertFollow(followVO);
//		}
		return new ResponseResult(HttpStatus.OK.value(), "OK", "Success");
	}

	public ResponseResult deleteFollow(String accesstoken, FollowVO followVO) {

		TokenVO tokenVO = userService.findTokenByToken(accesstoken);

		Long userId = tokenVO.getUserId();
		followVO.setFollowerId(userId);

		followDAO.deleteFollow(followVO);
		return new ResponseResult(HttpStatus.OK.value(), "OK", "Success");
	}
}
