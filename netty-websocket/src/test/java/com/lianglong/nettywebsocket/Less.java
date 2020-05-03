package com.lianglong.nettywebsocket;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lianglong
 * @date 2020/5/2
 */
@NoArgsConstructor
@Data
public class Less {


    /**
     * insurancePlan :
     * risks : [{"riskName":"昆仑健康保险股份有限公司E顺行航空意外伤害保险（2017版）","payMode":"缴费方式","cashValues":[{}],"insuredAmount":"1000000.00","payPeriod":"2018-02-15","riskPeriod":"2018-02-15","premiun":"100.00"}]
     * appnt : {"sex":"男","no":"0105836486","name":"李彦祖","certType":"身份证","certNo":"1231231242321312"}
     * totalPremiun : 陆拾万元整
     * image :
     * templateCode : A168
     * clauseUrl : http://www.kunlunhealth.com/templet/default/ShowArticle_xxpl.jsp?id=4776
     * CValiDate : 2018-02-08
     * insurancePlanNo : A168
     * polSpec : 无
     * insureds : [{"sex":"男","no":"0105836486","name":"李彦祖","certType":"身份证","certNo":"12312312312423123"},{"sex":"女","no":"0105836485","name":"韩雪","certType":"身份证","certNo":"12312312312"}]
     * contEndDate : 2018-02-15
     * bizNo : 1708041358198180SW01
     * comment : 备注
     * companyInfo : {"phone":"(86)4008118899","address":"北京市朝阳区建华南路6号院1号楼卓明大厦1层","zipCode":"100022","name":"昆仑健康北京分公司","serviceOrganiza":"深圳众诚泰保险经纪有限公司","url":"www.kunlunhealth.com"}
     */

    private String insurancePlan;
    private AppntEnity appnt;
    private String totalPremiun;
    private String image;
    private String templateCode;
    private String clauseUrl;
    private String CValiDate;
    private String insurancePlanNo;
    private String polSpec;
    private String contEndDate;
    private String bizNo;
    private String comment;
    private CompanyInfoEnity companyInfo;
    private List<RisksEnity> risks;
    private List<AppntEnity> insureds;

    @NoArgsConstructor
    @Data
    public static class AppntEnity {
        /**
         * sex : 男
         * no : 0105836486
         * name : 李彦祖
         * certType : 身份证
         * certNo : 1231231242321312
         */

        private String sex;
        private String no;
        private String name;
        private String certType;
        private String certNo;
    }

    @NoArgsConstructor
    @Data
    public static class CompanyInfoEnity {
        /**
         * phone : (86)4008118899
         * address : 北京市朝阳区建华南路6号院1号楼卓明大厦1层
         * zipCode : 100022
         * name : 昆仑健康北京分公司
         * serviceOrganiza : 深圳众诚泰保险经纪有限公司
         * url : www.kunlunhealth.com
         */

        private String phone;
        private String address;
        private String zipCode;
        private String name;
        private String serviceOrganiza;
        private String url;
    }

    @NoArgsConstructor
    @Data
    public static class RisksEnity {
        /**
         * riskName : 昆仑健康保险股份有限公司E顺行航空意外伤害保险（2017版）
         * payMode : 缴费方式
         * cashValues : [{}]
         * insuredAmount : 1000000.00
         * payPeriod : 2018-02-15
         * riskPeriod : 2018-02-15
         * premiun : 100.00
         */

        private String riskName;
        private String payMode;
        private String insuredAmount;
        private String payPeriod;
        private String riskPeriod;
        private String premiun;
        private List<CashValuesEnity> cashValues;

        @NoArgsConstructor
        @Data
        public static class CashValuesEnity {
        }
    }
}
