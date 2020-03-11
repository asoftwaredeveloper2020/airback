package com.airback.module.project.dao;

import com.airback.db.persistence.ICrudGenericDAO;
import com.airback.module.project.domain.ProjectNotificationSetting;
import com.airback.module.project.domain.ProjectNotificationSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings({ "ucd", "rawtypes" })
@Mapper
public interface ProjectNotificationSettingMapper extends ICrudGenericDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    long countByExample(ProjectNotificationSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    int deleteByExample(ProjectNotificationSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    int insert(ProjectNotificationSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    int insertSelective(ProjectNotificationSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    List<ProjectNotificationSetting> selectByExample(ProjectNotificationSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    ProjectNotificationSetting selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    int updateByExampleSelective(@Param("record") ProjectNotificationSetting record, @Param("example") ProjectNotificationSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    int updateByExample(@Param("record") ProjectNotificationSetting record, @Param("example") ProjectNotificationSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    int updateByPrimaryKeySelective(ProjectNotificationSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    int updateByPrimaryKey(ProjectNotificationSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    Integer insertAndReturnKey(ProjectNotificationSetting value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    void removeKeysWithSession(List primaryKeys);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_notifications
     *
     * @mbg.generated Fri Apr 12 22:39:46 CDT 2019
     */
    void massUpdateWithSession(@Param("record") ProjectNotificationSetting record, @Param("primaryKeys") List primaryKeys);
}