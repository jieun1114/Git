package com.jieun.recyclerview;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private List<RecyclerItem> list = new ArrayList<>();

    public List<RecyclerItem> getItems(){

        list.add(new RecyclerItem("경복궁","서울 종로구 사직로 161 경복궁","3000원","https://file.mk.co.kr/meet/neds/2017/10/image_readtop_2017_685605_15082265403064351.jpg"));
        list.add(new RecyclerItem("덕수궁","서울 중구 세종대로 99 덕수궁","1000원","http://www.deoksugung.go.kr/ko/assets/images/event/event11.jpg"));
        list.add(new RecyclerItem("창덕궁","서울 종로구 율곡로 99","3000원","http://heritage.unesco.or.kr/wp-content/uploads/2019/04/hd6_393_i1.jpg"));
        list.add(new RecyclerItem("창경궁","서울 종로구 와룡동 창경궁로 185","1000원","http://korean.visitseoul.net/comm/getImage?srvcId=MEDIA&parentSn=17968&fileTy=MEDIA&fileNo=1&thumbTy=L"));
        list.add(new RecyclerItem("종묘","서울 종로구 훈정동 종로 157","1000원","http://cfile211.uf.daum.net/image/2212B137572C6CE7298C18"));
        list.add(new RecyclerItem("경희궁","서울 종로구 사직동 새문안로 45","무료","https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Heunghwamun.jpg/1200px-Heunghwamun.jpg"));
        list.add(new RecyclerItem("북촌 한옥마을","서울 중구 남대문로4가 세종대로 40","무료","https://img.insight.co.kr/static/2018/06/14/700/4ag9g9vbsaxr3atx5vdo.jpg"));
        list.add(new RecyclerItem("롯데월드","서울 송파구 올림픽로 240","54000원","https://mticket.lotteworld.com/simgs/display/1210/upload/201703024ab0394260d04693b53f83046f41f6ad"));
        list.add(new RecyclerItem("석촌호수공원"," 서울 송파구 석촌호수로 197","무료","https://t1.daumcdn.net/cfile/tistory/2378D53A51A872510E"));
        list.add(new RecyclerItem("서울스카이","서울 송파구 올림픽로 300 117~123층","37000원","http://cdn.bizwatch.co.kr/news/photo/2017/03/05/ce3eb2eac6ef5d24c61b7f38901e1237141308.jpg"));
        //list.add(new RecyclerItem("","","",""));

        return list;
    }
}
