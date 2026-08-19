package com.fastcam.spserver.service;

import com.fastcam.spserver.dto.Paging;
import com.fastcam.spserver.entity.Qna;
import com.fastcam.spserver.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class QnaService {

    @Autowired
    QnaRepository qr;

    @Transactional(readOnly = true)
    public HashMap<String, Object> getQnaList(int page) {
        if (page < 1) page = 1;

        HashMap<String, Object> result = new HashMap<>();

        Paging paging = new Paging();
        paging.setPage(page);
        paging.setDisplayPage(5);
        paging.setDisplayRow(5);
        paging.setTotalCount((int) qr.count());
        paging.calPaing();

        Pageable pageable = PageRequest.of(
                page - 1,
                paging.getDisplayRow(),
                Sort.by(Sort.Direction.DESC, "qseq")
        );

        List<Qna> list = qr.findAll(pageable).getContent();
        result.put("qnaList", list);
        result.put("paging", paging);
        return result;

    }

    public Qna getQna(int num) {
        return qr.findByNum(num);
    }

    public void writeQna(Qna qna) {
        qr.save(qna);
    }

    public void deleteBoard(int num) {
        qr.deleteByNum(num);
    }
}
