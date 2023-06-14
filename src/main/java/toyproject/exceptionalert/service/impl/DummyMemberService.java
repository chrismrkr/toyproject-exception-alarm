package toyproject.exceptionalert.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import toyproject.exceptionalert.service.MemberService;

import java.util.ArrayList;
import java.util.List;


@Service
@Qualifier("dummyMemberService")
public class DummyMemberService implements MemberService {
    public List<String> getEmailList() {
        List<String> emailList = new ArrayList<>();
        emailList.add("b635032@gmail.com");
        emailList.add("b635032@g.hongik.ac.kr");
        return emailList;
    }
}
