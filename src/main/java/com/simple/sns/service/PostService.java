package com.simple.sns.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.simple.sns.domain.FollowVO;
import com.simple.sns.domain.PostAndUserVO;
import com.simple.sns.domain.PostVO;
import com.simple.sns.domain.ResponseResult;
import com.simple.sns.domain.TokenVO;
import com.simple.sns.repository.FollowDAO;
import com.simple.sns.repository.PostDAO;
import com.simple.sns.repository.UserDAO;

@Service
public class PostService {
	static org.slf4j.Logger logger = LoggerFactory.getLogger(PostService.class);

	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private FollowDAO followDAO;
	
	public int insertPostByTitleAndContent(PostVO postVO) {
		return postDAO.insertPostByTitleAndContent(postVO);
	}
	
	public PostVO findPostById(Long id) {
		return postDAO.findPostById(id);
	}
	
	// All post follow
	public List<PostAndUserVO> findPostsAndUserWithIsFollow(String accesstoken){
		TokenVO tokenVO =  userDAO.findTokenByToken(accesstoken);
		Long userId = tokenVO.getUserId();
		
		List<PostAndUserVO> findPostsAndUserWithIsFollows = postDAO.findPostsAndUserWithIsFollow(userId);
		
		for (PostAndUserVO findPostsAndUserVO : findPostsAndUserWithIsFollows) {
			Long postUserIdByPostVo = findPostsAndUserVO.getUserId();
				
			List<FollowVO> followVOs =  followDAO.findFollowVOsByFollowerId(userId);
			if(followVOs.size()==0 | followVOs.equals(null)) {
				if(postUserIdByPostVo ==userId ) {
					findPostsAndUserVO.getUser().setIsFollow(null);
				}else {
					findPostsAndUserVO.getUser().setIsFollow(false);
				}
			}else {
				for (FollowVO followVO : followVOs) {
					Long followeeIdByFollowVO =  followVO.getFolloweeId();
					if(postUserIdByPostVo == userId | postUserIdByPostVo.equals(userId)) {
						findPostsAndUserVO.getUser().setIsFollow(null);
						break;
					} else if(postUserIdByPostVo == followeeIdByFollowVO) {
						findPostsAndUserVO.getUser().setIsFollow(true);
						break;
					} else {
						findPostsAndUserVO.getUser().setIsFollow(false);
					}
				}
			}
		}
		return findPostsAndUserWithIsFollows;
	}

	public List<PostAndUserVO> findPostsAndUser() {
		return postDAO.findPostsAndUser();
	}
	
	public List<PostAndUserVO> findPostAndUserByUserId(Long userId){
		return postDAO.findPostAndUserByUserId(userId);
	}
	
	public List<PostAndUserVO> findPostAndUserByToken(String token){
		return postDAO.findPostAndUserByToken(token);
	}
	
	public PostAndUserVO findPostAndUserByPostId(Long postId) {
		return postDAO.findPostAndUserByPostId(postId);
	}

	public void deletePostByPostId(Long postId) {
		postDAO.deletePostByPostId(postId);
	}

	public int updatePostTitleAndContent(PostVO postVO) {
		return postDAO.updatePostTitleAndContent(postVO);
	}

	//MyFeed
	public ResponseResult findMyPostAndUserAndMyFollowerByUserId(String accesstoken) {
		TokenVO tokenVO = userDAO.findTokenByToken(accesstoken);
		Long userId = tokenVO.getUserId();
		List<PostAndUserVO> postAndUserVOs = postDAO.findMyPostAndUserAndMyFollowerByUserId(userId);
		
		for (PostAndUserVO postAndUserVO : postAndUserVOs) {
			List<FollowVO> followVOs =  followDAO.findFollowVOsByFollowerId(userId);
			
			Long postUserIdByPostVo = postAndUserVO.getUserId();
			if(followVOs.size()==0 | followVOs.equals(null)) {
				if(postUserIdByPostVo ==userId ) {
					postAndUserVO.getUser().setIsFollow(null);
				}else {
					postAndUserVO.getUser().setIsFollow(false);
				}
			}else {
				for (FollowVO followVO : followVOs) {
					Long followeeIdByFollowVO =  followVO.getFolloweeId();
					if(postUserIdByPostVo == userId | postUserIdByPostVo.equals(userId)) {
						postAndUserVO.getUser().setIsFollow(null);
						break;
					} else if(postUserIdByPostVo == followeeIdByFollowVO) {
						postAndUserVO.getUser().setIsFollow(true);
						break;
					} else {
						postAndUserVO.getUser().setIsFollow(false);
					}
				}
			}
		}
		return new ResponseResult(HttpStatus.OK.value(), "Success", postAndUserVOs ); 
	}
}
