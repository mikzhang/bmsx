package com.ran.bmsx.system.dao;


import com.ran.bmsx.system.model.LoginRecord;
import com.ran.bmsx.system.model.LoginRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LoginRecordMapper {
    int countByExample(LoginRecordExample example);

    int deleteByExample(LoginRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(LoginRecord record);

    int insertSelective(LoginRecord record);

    List<LoginRecord> selectByExample(LoginRecordExample example);

    LoginRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") LoginRecord record, @Param("example") LoginRecordExample example);

    int updateByExample(@Param("record") LoginRecord record, @Param("example") LoginRecordExample example);

    int updateByPrimaryKeySelective(LoginRecord record);

    int updateByPrimaryKey(LoginRecord record);
    
    public List<LoginRecord> selectLoginRecords(@Param("startDate") String startDate, @Param("endDate") String endDate,
                                                @Param("searchAccount") String searchAccount);
}