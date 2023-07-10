package com.datn.dongho5s.GiaoHangNhanhService;

import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.BaseListResponse;
import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.BaseResponse;
import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.PhiVanChuyenResponse;
import com.datn.dongho5s.GiaoHangNhanhService.Request.PhiVanChuyenRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class DonHangAPI {

    private static final String FeeAPI = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";

    public static Integer getFee(PhiVanChuyenRequest request) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(FeeAPI);
        httpPost.setHeader("Token", Constant.TOKEN);
        httpPost.setHeader("shopid", String.valueOf(Constant.ID_SHOP));
        request.getTrungBinhCacCanh();
            try {
                StringBuilder body = new StringBuilder("{ \"to_district_id\":" + request.getIdQuanHuyen() + " }");
                body.append("{ \"to_ward_code\":" + request.getIdPhuongXa() + " }");
                body.append("{ \"height\":" + request.getTrungBinhCacCanh() + " }");
                body.append("{ \"length\":" + request.getTrungBinhCacCanh() + " }");
                body.append("{ \"width\":" + request.getTrungBinhCacCanh() + " }");
                body.append("{ \"weight\":" + request.getSoLuongSanPham()*Constant.TRONG_LUONG_SAN_PHAM + " }");
                StringEntity requestEntity = new StringEntity(body.toString());
                httpPost.setEntity(requestEntity);
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                System.out.println(responseBody);
                //Mapper
                ObjectMapper objectMapper = new ObjectMapper();
                BaseResponse<PhiVanChuyenResponse> responseObject = objectMapper.readValue(responseBody, new TypeReference<>() {
                });
                PhiVanChuyenResponse thongTinPhi = responseObject.getData();
                return thongTinPhi.getTotal();
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
    }
}
