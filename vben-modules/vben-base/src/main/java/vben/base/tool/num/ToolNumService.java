package vben.base.tool.num;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.DateUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ToolNumService {

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    public String getDateNum(ToolNum num, String dateType) {
        //获取 是否被修改过或新添加的 字段的值
        //如果  是否被修改过或新添加的=='Y'
        if (num.getNflag()) {
            //生成第一个流水号 0001
            String firstSerialNum = getFirstSerialNum(num.getNulen());
            //计算下一个流水号 0002
            String nextSerialNum = getNextSerialNum(firstSerialNum);
            //获取系统的当前时间 格式yyyyMMdd  20220906
            String curDate = DateUtils.format(new Date(), dateType);

            //生成客户编码
            //编码前缀+"-"+利用日期位格式生成当前的日期[yyyyMMdd ]+"-"+0001  c-20220914-0001
            String back = (num.getNupre() == null ? "" : num.getNupre()) + curDate + firstSerialNum;
            //修改代码规则表
            //下一个流水号="0002"
            num.setNunex(nextSerialNum);
            //当前日期  20140907
            num.setCudat(curDate);
            //是否被修改过='N'
            num.setNflag(false);
            dao.update(num);
            return back;
        } else {
            //是否被修改过或新添加的=='N'
            //获取代码规则表中的当前日期字段的值
            String curDate = num.getCudat();
            //获取系统的当前日期
            String sysCurDate = DateUtils.format(new Date(), dateType);
            //如果代码规则表中的当前日期字段的值==系统的当前日期
            if (curDate.equals(sysCurDate)) {
                //获取下一个流水号 ="0002"
                String nextseq = num.getNunex();
                //计算新的流水号 0003
                String nextSerialNum = getNextSerialNum(nextseq);
                //生成客户编码
                //编码前缀+"-"+利用日期位格式生成当前的日期[yyyyMMdd ]+"-"+0001
                String back = (num.getNupre() == null ? "" : num.getNupre()) + sysCurDate + nextseq;
                //修改代码规则表
                //下一个流水号="0003"
                num.setNunex(nextSerialNum);
                //当前日期  20140908
                //是否被修改过='N'
                dao.update(num);
                return back;
            } else { //如果代码规则表中的当前日期字段的值!=系统的当前日期、

                //生成第一个流水号 0001
                String firstSerialNum = getFirstSerialNum(num.getNulen());
                //计算下一个流水号 0002
                String nextSerialNum = getNextSerialNum(firstSerialNum);
                //生成客户编码
                //编码前缀+"-"+利用日期位格式生成当前的日期[yyyyMMdd ]+"-"+0001
                String back = (num.getNupre() == null ? "" : num.getNupre()) + sysCurDate + firstSerialNum;
                //修改代码规则表
                //下一个流水号="0002"
                num.setNunex(nextSerialNum);
                //当前日期  20110915
                num.setCudat(sysCurDate);
                //是否被修改过='N'
                num.setNflag(false);
                dao.update(num);
                return back;
            }
        }
    }


    public String getPureNum(ToolNum num) {
        if (num.getNflag()) {
            String firstSerialNum = getFirstSerialNum(num.getNulen());
            String nextSerialNum = getNextSerialNum(firstSerialNum);
            num.setNunex(nextSerialNum);
            num.setNflag(false);
            dao.update(num);
            return (num.getNupre() == null ? "" : num.getNupre()) + firstSerialNum;
        } else {
            String nextseq = num.getNunex();
            String nextSerialNum = getNextSerialNum(nextseq);
            num.setNunex(nextSerialNum);
            dao.update(num);
            return (num.getNupre() == null ? "" : num.getNupre()) + nextseq;
        }
    }

//     <select name="mode">
//      <option value="uuid">UUID</option>
//      <option value="manual">手动改</option>
//      <option value="nodate">无日期</option>
//      <option value="yyyymmdd">年月日YYYYMMDD</option>
//      <option value="yymmdd">年月日YYMMDD</option>
//      <option value="yyyymm">年月YYYYMM</option>
//      <option value="yymm">年月YYMM</option>
//      <option value="yy">年YY</option>
//     </select>

    public String getNum(String id){
        ToolNum num = dao.findById(id);
        String number = "";
        switch(num.getNumod()){
            case "nodate":
                number = getPureNum(num);
                break;
            case "YYYYMMDD":
                number = getDateNum(num,"yyyyMMdd");
                break;
            case "YYMMDD":
                number = getDateNum(num,"yyMMdd");
                break;
            case "YYYYMM":
                number = getDateNum(num,"yyyyMM");
                break;
            case "YYMM":
                number = getDateNum(num,"yyMM");
                break;
            case "YY":
                number = getDateNum(num,"yy");
                break;
        }
        return number;
    }


    // 利用给定的流水位生成第一个流水号
    private String getFirstSerialNum(int zlength) {
        String serialNum = "";
        //流水号为4里下面取3
        for (int i = 0; i < zlength - 1; i++) {
            serialNum = serialNum + "0";
        }
        serialNum = serialNum + "1";
        return serialNum;
    }

    //根据当前的流水号，生成下一个流水号
    private String getNextSerialNum(String curSerialNum) {
        curSerialNum = "1" + curSerialNum;
        Integer icurSerialNum = Integer.parseInt(curSerialNum);
        icurSerialNum++;
        curSerialNum = icurSerialNum + "";
        curSerialNum = curSerialNum.substring(1);
        return curSerialNum;
    }

    public ToolNum findById(String id) {
        return dao.findById(id);
    }

    public void insert(ToolNum main) {
        dao.insert(main);
    }

    public void update(ToolNum main) {
        dao.update(main);
    }

    public int delete(String[] ids) {
        for (String id : ids) {
            dao.deleteById(id);
        }
        return ids.length;
    }

    public boolean existsById(String id) {
        return dao.existsById(id);
    }

    private final ToolNumDao dao;


}
