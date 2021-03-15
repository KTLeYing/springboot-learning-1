package com.mzl.qqcomment.service.impl;

import com.mzl.qqcomment.dao.CommentRepository;
import com.mzl.qqcomment.dto.ReplyDTO;
import com.mzl.qqcomment.entity.ReplyInfo;
import com.mzl.qqcomment.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName :   CommentServiceImpl
 * @Description: 评论业务逻辑实现层
 * @Author: mzl
 * @CreateDate: 2021/1/10 20:58
 * @Version: 1.0
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    /**
     * 获取文章列表
     * @param comId
     * @return
     */
    @Override
    public List<ReplyDTO> getReplyList(int comId) {
        //查询出某条评论内容的所有的 评论(其他人)和回复(主人)的 列表
        List<ReplyInfo> replyInfoList = commentRepository.findByComIdOrderByUpdateTime(comId);
        //把实体类的属性值赋值给对应的dto对象
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        System.out.println("所有的评论记录：");
        replyInfoList.forEach(e -> {
            ReplyDTO replyDTO = new ReplyDTO();
            BeanUtils.copyProperties(e, replyDTO);
            replyDTOList.add(replyDTO);
            System.out.println(replyDTO);
        });

//            replyDTOList.forEach(e -> {
//                    System.out.println(e);
//            });

        /**
         * 1、如果其他评论者的replyId为空，说明评论者是初次评论，主人还没回复；2、如果其他评论者的replyId不为空，说明主人已经回复了该评论者了（双方可能多次
         * 交流了），然后该评论者又回复该主人；3、如果主人的replyId不为空，说明主人回复了评论者（双方可能多次交流了,主人的replyId不可能为空的，而评论者可能为空，
         * 因为是评论者先发出评论的）【注意：只能是评论者和主人直接的交流，不能是评论者之间】
         */
        // 本文主要说明在Java8及以上版本中，使用stream().filter()来过滤一个List对象，查找符合条件的对象集合(自动转为list)。
        // 过滤筛选出只有其他人评论但主人没回复的（即replyId为空的）
        List<ReplyDTO> noReplyList =
                replyDTOList.stream()
                        .filter(e -> StringUtils.isEmpty(e.getReplyId()))
                        .collect(Collectors.toList());
        System.out.println("评论开始的（replyId为null）：");
        noReplyList.forEach(
                e -> {
                    System.out.println(e);
                });

        //过滤筛选出其他人评论了且主人也回复了的，可能进行多轮交流了（即replyId不为空的）
        List<ReplyDTO> replyList = replyDTOList.stream().filter(e -> !StringUtils.isEmpty(e.getReplyId())).collect(Collectors.toList());
        System.out.println("非评论开始的（replyId不为null）：");
        replyList.forEach(
                e -> {
                    System.out.println(e);
                });

        //创建一个list来存储最终结果(包括每一个开始的记录（从评论者开始）及其对应的双方相互交流的回复记录或没有)
        List<ReplyDTO> resultReplyList = new ArrayList<>();

        // 对只有其他人评论但主人没回复的列表遍历，开始点（每次处理都是从评论者的replyId为null的开始找，找出每一个开始的记录（从评论者开始）对应的双方相互交流的回复记录）
        noReplyList.forEach(
                e -> {
                    System.out.println("开始的评论的评论者为：" + e);
                    ReplyDTO replyDTO = new ReplyDTO(); // 创建一个新的replyDTO对象
                    BeanUtils.copyProperties(e, replyDTO); // 把遍历的对象赋值给replyDTO对象
                    // 创建一个list来存储主人回到评论者和评论者又评论的列表（包含多次），每次处理都是从评论者的replyId为null的开始找
                    List<ReplyDTO> replyDTOList2 = new ArrayList<>();
                    getList(replyList, e.getId(), replyDTOList2);

                    System.out.println("主人回到评论者和评论者又评论的列表（包含多次）排序前:");
                    replyDTOList2.forEach(
                            e1 -> {
                                System.out.println(e1);
                            });

                    // 对存储主人回到评论者和评论者又评论的列表进行排序（开始默认升序，reversed后为降序）
//                    replyDTOList2.sort(
//                            Comparator.comparing(ReplyDTO::getUpdateTime)
//                                    .reversed()); // ReplyDTO::getUpdateTime<=>replyInfo -> replyInfo.getUpdateTime（）
                    replyDTOList2.sort(
                            Comparator.comparing(ReplyDTO::getUpdateTime)); // ReplyDTO::getUpdateTime<=>replyInfo -> replyInfo.getUpdateTime（）

                    System.out.println("主人回到评论者和评论者又评论的列表（包含多次）排序后");
                    replyDTOList2.forEach(
                            e1 -> {
                                System.out.println(e1);
                            });

                    // 设置每一个开始的记录（从评论者开始）对应的双方相互交流的回复记录
                    replyDTO.setReplyInfoList(replyDTOList2);

                    System.out.println("每一个开始的记录（从评论者开始）及其对应的双方相互交流的回复记录：");
                    System.out.println(replyDTO);

                    // 把每一个开始的记录（从评论者开始）对应的双方相互交流的回复记录或没有的加到结果中(每次处理都是从评论者的replyId为null的开始找的那个作为一个添加的记录)
                    resultReplyList.add(replyDTO);

                    System.out.println("**********************" + "\n");
                });

        System.out.println("最终的结果遍历（每一个开始的记录（从评论者开始）及其对应的双方相互交流的回复记录）：");
        resultReplyList.forEach(
                e -> {
                    System.out.println(e);
                });
        return resultReplyList;
    }

    /**
     * 获取主人和评论者之间至少有一次交流的，可能包含多次
     * @param replyList
     * @param id
     * @param replyDTOList2
     */
    private void getList(List<ReplyDTO> replyList, int id, List<ReplyDTO> replyDTOList2) {
        List<ReplyDTO> firstList = replyList.stream().filter(e -> e.getReplyId().equals(id)).collect(Collectors.toList());
        System.out.println("寻找自己的回复的记录为：");
        firstList.forEach(
                e -> {
                    System.out.println(e);
                });
        //如果主人回复了这个评论者(首次)，继续遍历看评论者是否有回复了刚刚主人的...以此类推，即双方之间相互交替回复交流，最后直到最后一个replyId为空，
        //即评论者者评论了某一条评论，id=1,replyId=null->id=2,replyId=1(指向主键id)[主人回复评论者]->id=4,replyId=2[评论者又回复了主人]->.......
        //使用递归来实现，直到某个其中一方没有回应了（即主键id在所有的记录中找不到对应的replyId了,ReplyInfo.id == replyInfo.replyId）
        if(!CollectionUtils.isEmpty(firstList)){   //如果另一方回复了一方（即主键id在所有的记录中找不到对应的replyId），继续遍历，直到id找不到对应的replyId
            replyDTOList2.addAll(firstList); //添加记录到存储主人回复评论者和评论者又评论的列表里（包含多次）
            firstList.forEach(
                    e -> {
                        getList(replyList, e.getId(), replyDTOList2);  //递归继续遍历
                    });
        }
    }

    /**
     * 删除某一条评论
     */
    @Override
    public void removeRely(int id) {
        commentRepository.deleteById(id);
    }


}
