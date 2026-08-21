package com.fastcam.spserver.service;

import com.fastcam.spserver.entity.Faq;
import com.fastcam.spserver.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FaqService {

    @Autowired
    FaqRepository fr;

    public List<Faq> getFaq() {
        return fr.findAll();
    }
}
