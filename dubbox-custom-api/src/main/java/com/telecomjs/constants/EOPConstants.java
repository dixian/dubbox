package com.telecomjs.constants;

/**
 * Created by zark on 16/11/17.
 */
public class EOPConstants {
    public static final short ASYNC_REQUEST_MESSAGE_PARAM_TYPE_STRING = 1;
    public static final short ASYNC_REQUEST_MESSAGE_PARAM_TYPE_MAP = 2;
    public static final String M_CALL_QRY_CUST_CUSTINFO = "qry.cust.custinfo";
    public static final String M_CALL_QRY_CUST_CUSTINFO_PARAM1 = "customId";
    public static final String M_CALL_QRY_CUST_CUSTINFO_BYNBR = "qry.cust.custinfobynbr";
    public static final String M_CALL_QRY_CUST_CUSTINFO_BYNBR_PARAM1 = "accNbr";
    public static final String M_CALL_QRY_CUST_PRODUCT_BYNBR = "qry.cust.prodinfobynbr";
    public static final String M_CALL_QRY_CUST_PRODUCT_BYNBR_PARAM1 = "accNbr";

    public  static final int TCPCONTENT_OK_RSPTYPE  = 0;
    public  static final String TCPCONTENT_OK_RSPCODE  = "0000";
    public  static final String TCPCONTENT_OK_RSPDESC  = "操作成功";

    public  static final int TCPCONTENT_ERROR_RSPTYPE  = -1;
    public  static final String TCPCONTENT_ERROR_RSPCODE  = "0001";
    public  static final String TCPCONTENT_ERROR_RSPDESC  = "操作失败";

    public static final String APP_ONLINE_STATUS_RDY = "RDY";
    public static final String APP_ONLINE_STATUS_END = "END";
}
