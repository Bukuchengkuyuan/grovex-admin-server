package com.grovex.admin.modules.csj.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grovex.admin.modules.csj.dto.AdDataDto;
import com.grovex.admin.modules.csj.dto.CsjResponseDto;
import com.grovex.admin.modules.csj.entity.CsjData;
import com.grovex.admin.modules.csj.service.CsjDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 查询数据工具方法
 */
@Component
public class DataCatch {

    @Value("${csj.api.url}")  // 从配置文件读取穿山甲 API 地址
    private String CSJ_API_URL;

    @Autowired
    private CsjDataService csjDataService;

    public static String getMD5Str(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder md5StrBuilder = new StringBuilder();
        for (byte tmp : Objects.requireNonNull(digest)) {
            md5StrBuilder.append(String.format("%02x", tmp & 0xff));
        }
        return md5StrBuilder.toString();
    }

    // 修改 getSign 方法，接收参数字符串而不是 Map
    public static String getSign(String params, String token) {
        String str = params + token;
        return getMD5Str(str);
    }

    public List<String> getDateList(String startDate, String endDate) {
        List<String> dateList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        while (!start.isAfter(end)) {
            dateList.add(start.format(formatter));
            start = start.plusDays(1);
        }

        return dateList;
    }
    public List<CsjData> convert(List<AdDataDto> adDataDtos) {
        List<CsjData> csjDataList = new ArrayList<>();

        for (AdDataDto adDataDto : adDataDtos) {
            CsjData csjData = new CsjData();
            csjData.setTimeZone(String.valueOf(adDataDto.getTime_zone()));
            csjData.setCurrency(adDataDto.getCurrency());
            csjData.setRegion(adDataDto.getRegion());
            csjData.setAppId(adDataDto.getApp_id());
            csjData.setAppName(adDataDto.getApp_name());
            csjData.setAdSlotId((int) adDataDto.getAd_slot_id());
            csjData.setAdSlotType(adDataDto.getAd_slot_type());
            csjData.setAdSlotTypeV2(adDataDto.getAd_slot_type_v2());
            csjData.setRequest(adDataDto.getAd_request());
            csjData.setReturnCount(adDataDto.getReturnCount());
            csjData.setFillRate(adDataDto.getFill_rate());
            csjData.setShowCount(adDataDto.getShow());
            csjData.setClick(adDataDto.getClick());
            csjData.setClickRate(adDataDto.getClick_rate());
            csjData.setRevenue(adDataDto.getRevenue());
            csjData.setEcpm(adDataDto.getEcpm());
            csjData.setPackageName(adDataDto.getPackage_name());
            csjData.setUseMediation(adDataDto.getUse_mediation());
            csjData.setBiddingType(adDataDto.getBidding_type());
            csjData.setAdRequest(adDataDto.getAd_request());
            csjData.setResponse(adDataDto.getResponse());
            csjData.setAdFillRate(adDataDto.getAd_fill_rate());
            csjData.setAdImpressionRate(adDataDto.getAd_impression_rate());
            csjData.setDate(adDataDto.getDate());
            csjData.setOs(adDataDto.getOs());

            csjDataList.add(csjData);
        }

        return csjDataList;
    }

    public void saveOrUpdateWithCompositeKey(CsjData csjData) {
        // 根据联合主键查询数据库
        CsjData existingData = csjDataService.getOne(new QueryWrapper<CsjData>()
                .eq("date", csjData.getDate())
                .eq("ad_slot_id", csjData.getAdSlotId()));

        if (existingData != null) {
            // 如果存在，则更新
            csjDataService.update(csjData,new QueryWrapper<CsjData>()
                    .eq("date", csjData.getDate())
                    .eq("ad_slot_id", csjData.getAdSlotId()));
        } else {
            // 如果不存在，则插入
            csjDataService.save(csjData);
        }
    }

    public void getCsjDateDaily(String startDate,String endDate, String token){
        List<String> dateList = getDateList(startDate, endDate);
        dateList.forEach(o->{
            getCsjDateDaily(o, token);
        });
    }

    public List<CsjData> getCsjDateDaily(String date, String token){
        String params = String.format("currency=cny&date=%s&region=cn&role_id=%d&sign_type=MD5&user_id=%d&version=2.0",
                date,161505, 161505);
        // 构建完整的请求 URL
        String uri = CSJ_API_URL + "/union_media/open_api/rt/income?" + params + "&sign=" + getSign(params, token);

        // 使用 RestTemplate 发送 HTTP 请求
        RestTemplate restTemplate = new RestTemplate();

        CsjResponseDto result = restTemplate.getForObject(uri, CsjResponseDto.class);

        List<AdDataDto> adDataDtos = result.getData().get(date);

        List<CsjData> csjDataList = convert(adDataDtos);

        csjDataList.stream().forEach(o->{
            saveOrUpdateWithCompositeKey(o);
        });

        return csjDataList;
    }
}
