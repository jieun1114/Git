package com.jieun.recyclerview;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private List<RecyclerItem> list = new ArrayList<>();
    private List<RecyclerItem> list2 = new ArrayList<>();
    private List<RecyclerItem> list3 = new ArrayList<>();
    private List<RecyclerItem> list4 = new ArrayList<>();

    public List<RecyclerItem> getItems1(){// 문화

        list.add(new RecyclerItem("경복궁","서울 종로구 사직로 161 경복궁","3,000","18007","72"));
        list.add(new RecyclerItem("덕수궁","서울 중구 세종대로 99 덕수궁","1,000","17995","2049"));
        list.add(new RecyclerItem("창덕궁","서울 종로구 율곡로 99","3,000","17999", "295"));
        list.add(new RecyclerItem("창경궁","서울 종로구 와룡동 창경궁로 185","1,000","17968", "297"));
        list.add(new RecyclerItem("종묘","서울 종로구 훈정동 종로 157","1,000","17304","507"));
        list.add(new RecyclerItem("경희궁","서울 종로구 사직동 새문안로 45","0","16480","1159"));
        list.add(new RecyclerItem("서울시립미술관","서울 중구 덕수궁길 61","0","10031","1525"));
        list.add(new RecyclerItem("전쟁기념관","서울 용산구 이태원로 29","0","16896","859"));
        list.add(new RecyclerItem("예술의전당","서울 서초구 남부순환로 2406","0","18461","24635"));
        list.add(new RecyclerItem("국립현대미술관 서울관","서울 종로구 삼청로 30","4,000","12792","11659"));

        return list;
    }

    public List<RecyclerItem> getItems2(){ // 액티비티

        list2.add(new RecyclerItem("롯데월드 어드벤처","서울 송파구 올림픽로 240","54,000","18437","2193"));
        list2.add(new RecyclerItem("남산서울타워","서울 용산구 남산공원길 105","10,000","17972","36"));
        list2.add(new RecyclerItem("석촌호수공원"," 서울 송파구 석촌호수로 197","0","17980","1136"));
        list2.add(new RecyclerItem("어린이대공원","서울 광진구 능동로 216","0","9815","1853"));
        list2.add(new RecyclerItem("반포 한강공원","서울 서초구 신반포로11길 39","0","2328","3968"));
        list2.add(new RecyclerItem("이촌-한강공원","서울 용산구 이촌로72길 62","0","2184","3617"));
        list2.add(new RecyclerItem("올림픽공원","서울 송파구 올림픽로 424","0","16364","2139"));
        list2.add(new RecyclerItem("코엑스 아쿠아리움","서울 강남구 영동대로 513 코엑스몰 B1","28,000","264","374"));
        list2.add(new RecyclerItem("낙산공원","서울 종로구 낙산길 41","0","18011","3701"));
        list2.add(new RecyclerItem("잠실야구장","서울 송파구 올림픽로 25","0","1706","2643"));


        return list2;
    }

    public List<RecyclerItem> getItems3(){ //복합

        list3.add(new RecyclerItem("롯데월드타워","서울 송파구 올림픽로 300 117~123층","0","14920","21278"));
        list3.add(new RecyclerItem("코엑스","서울 강남구 영동대로 513","0","18469","24663"));
        list3.add(new RecyclerItem("63스퀘어","서울 영등포구 63로 50","20,000","17985","252"));
        list3.add(new RecyclerItem("DDP(동대문디자인플라자)","서울 중구 을지로 281","0","18003","95"));
        list3.add(new RecyclerItem("서울광장","서울 중구 세종대로 110","0","888","1539"));
        list3.add(new RecyclerItem("신사동 가로수길","서울 강남구 압구정로 120  일대","0","235","291"));
        list3.add(new RecyclerItem("청계천","서울 종로구 무교로 37","0","18022","34"));
        list3.add(new RecyclerItem("KT&G 상상마당","서울 마포구 어울마당로 65","0","18435","24561"));
        list3.add(new RecyclerItem("광화문광장","서울 종로구 세종대로 172","0","17991","1900"));
        list3.add(new RecyclerItem("대학로","서울 종로구 혜화로 12","0","17090","2107"));

        return list3;

    }

    public List<RecyclerItem> getItems4(){ // top10
        list4.add(new RecyclerItem("코엑스","서울 강남구 영동대로 513","0","18469","24663"));
        list4.add(new RecyclerItem("롯데월드 어드벤처","서울 송파구 올림픽로 240","54,000","18437","2193"));
        list4.add(new RecyclerItem("덕수궁","서울 중구 세종대로 99 덕수궁","1,000","17995","2049"));
        list4.add(new RecyclerItem("올림픽공원","서울 송파구 올림픽로 424","0","16364","2139"));
        list4.add(new RecyclerItem("DDP(동대문디자인플라자)","서울 중구 을지로 281","0","18003","95"));
        list4.add(new RecyclerItem("서울시립미술관","서울 중구 덕수궁길 61","0","10031","1525"));
        list4.add(new RecyclerItem("예술의전당","서울 서초구 남부순환로 2406","0","18461","24635"));
        list4.add(new RecyclerItem("서울광장","서울 중구 세종대로 110","0","888","1539"));
        list4.add(new RecyclerItem("낙산공원","서울 종로구 낙산길 41","0","18011","3701"));
        list4.add(new RecyclerItem("전쟁기념관","서울 용산구 이태원로 29","0","16896","859"));

        return list4;
    }
}
