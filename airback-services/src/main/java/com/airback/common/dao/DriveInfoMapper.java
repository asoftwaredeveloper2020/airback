package com.airback.common.dao;

import com.airback.common.domain.DriveInfo;
import com.airback.common.domain.DriveInfoExample;
import com.airback.db.persistence.ICrudGenericDAO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings({ "ucd", "rawtypes" })
@Mapper
public interface DriveInfoMapper extends ICrudGenericDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    long countByExample(DriveInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    int deleteByExample(DriveInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    int insert(DriveInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    int insertSelective(DriveInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    List<DriveInfo> selectByExample(DriveInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    DriveInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    int updateByExampleSelective(@Param("record") DriveInfo record, @Param("example") DriveInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    int updateByExample(@Param("record") DriveInfo record, @Param("example") DriveInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    int updateByPrimaryKeySelective(DriveInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    int updateByPrimaryKey(DriveInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    Integer insertAndReturnKey(DriveInfo value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    void removeKeysWithSession(List primaryKeys);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_driveinfo
     *
     * @mbg.generated Sat Feb 09 11:42:26 CST 2019
     */
    void massUpdateWithSession(@Param("record") DriveInfo record, @Param("primaryKeys") List primaryKeys);
}