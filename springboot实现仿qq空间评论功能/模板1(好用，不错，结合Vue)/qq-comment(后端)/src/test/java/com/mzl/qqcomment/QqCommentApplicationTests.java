package com.mzl.qqcomment;

import com.mzl.qqcomment.dao.CommentRepository;
import com.mzl.qqcomment.dto.ReplyDTO;
import com.mzl.qqcomment.entity.ReplyInfo;
import com.mzl.qqcomment.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QqCommentApplicationTests {

	@Autowired
	private CommentService commentService;

	@Test
	public void getReplyList() {
		int comId = 1;
		commentService.getReplyList(comId);
	}


}
