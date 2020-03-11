/*Domain class of table m_form_section*/
package com.airback.form.domain;

import com.airback.core.arguments.ValuedBean;
import com.airback.db.metadata.Column;
import com.airback.db.metadata.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

@SuppressWarnings("ucd")
@Table("m_form_section")
@Alias("FormSection")
public class FormSection extends ValuedBean {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_form_section.id
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    @Column("id")
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_form_section.name
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    @NotEmpty
    @Length(max=100, message="Field value is too long")
    @Column("name")
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_form_section.layoutIndex
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    @NotNull
    @Column("layoutIndex")
    private Integer layoutindex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_form_section.module
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    @NotEmpty
    @Length(max=45, message="Field value is too long")
    @Column("module")
    private String module;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_form_section.sAccountId
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    @NotNull
    @Column("sAccountId")
    private Integer saccountid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_form_section.isDeleteSection
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    @NotNull
    @Column("isDeleteSection")
    private Boolean isdeletesection;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_form_section.layoutType
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    @NotNull
    @Column("layoutType")
    private Integer layouttype;

    private static final long serialVersionUID = 1;

    public final boolean equals(Object obj) {
        if (obj == null) { return false;}
        if (obj == this) { return true;}
        if (!obj.getClass().isAssignableFrom(getClass())) { return false;}
        FormSection item = (FormSection)obj;
        return new EqualsBuilder().append(id, item.id).build();
    }

    public final int hashCode() {
        return new HashCodeBuilder(847, 1973).append(id).build();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_form_section.id
     *
     * @return the value of m_form_section.id
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_form_section
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public FormSection withId(Integer id) {
        this.setId(id);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_form_section.id
     *
     * @param id the value for m_form_section.id
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_form_section.name
     *
     * @return the value of m_form_section.name
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_form_section
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public FormSection withName(String name) {
        this.setName(name);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_form_section.name
     *
     * @param name the value for m_form_section.name
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_form_section.layoutIndex
     *
     * @return the value of m_form_section.layoutIndex
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public Integer getLayoutindex() {
        return layoutindex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_form_section
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public FormSection withLayoutindex(Integer layoutindex) {
        this.setLayoutindex(layoutindex);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_form_section.layoutIndex
     *
     * @param layoutindex the value for m_form_section.layoutIndex
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public void setLayoutindex(Integer layoutindex) {
        this.layoutindex = layoutindex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_form_section.module
     *
     * @return the value of m_form_section.module
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public String getModule() {
        return module;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_form_section
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public FormSection withModule(String module) {
        this.setModule(module);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_form_section.module
     *
     * @param module the value for m_form_section.module
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_form_section.sAccountId
     *
     * @return the value of m_form_section.sAccountId
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public Integer getSaccountid() {
        return saccountid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_form_section
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public FormSection withSaccountid(Integer saccountid) {
        this.setSaccountid(saccountid);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_form_section.sAccountId
     *
     * @param saccountid the value for m_form_section.sAccountId
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public void setSaccountid(Integer saccountid) {
        this.saccountid = saccountid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_form_section.isDeleteSection
     *
     * @return the value of m_form_section.isDeleteSection
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public Boolean getIsdeletesection() {
        return isdeletesection;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_form_section
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public FormSection withIsdeletesection(Boolean isdeletesection) {
        this.setIsdeletesection(isdeletesection);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_form_section.isDeleteSection
     *
     * @param isdeletesection the value for m_form_section.isDeleteSection
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public void setIsdeletesection(Boolean isdeletesection) {
        this.isdeletesection = isdeletesection;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_form_section.layoutType
     *
     * @return the value of m_form_section.layoutType
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public Integer getLayouttype() {
        return layouttype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_form_section
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public FormSection withLayouttype(Integer layouttype) {
        this.setLayouttype(layouttype);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_form_section.layoutType
     *
     * @param layouttype the value for m_form_section.layoutType
     *
     * @mbg.generated Thu Mar 28 09:43:58 CDT 2019
     */
    public void setLayouttype(Integer layouttype) {
        this.layouttype = layouttype;
    }

    public enum Field {
        id,
        name,
        layoutindex,
        module,
        saccountid,
        layouttype,
        isdeletesection;

        public boolean equalTo(Object value) {
            return name().equals(value);
        }
    }
}