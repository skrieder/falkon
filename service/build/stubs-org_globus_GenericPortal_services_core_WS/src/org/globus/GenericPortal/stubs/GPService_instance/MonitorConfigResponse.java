/**
 * MonitorConfigResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class MonitorConfigResponse  implements java.io.Serializable {
    private int maxBundling;
    private boolean piggyBacking;
    private boolean preFetching;
    private java.lang.String drpAllocation;
    private int drpMinResc;
    private int drpMaxResc;
    private int drpMinTime;
    private int drpMaxTime;
    private int drpIdle;
    private java.lang.String scheduler;
    private boolean dataCaching;
    private boolean valid;

    public MonitorConfigResponse() {
    }

    public MonitorConfigResponse(
           boolean dataCaching,
           java.lang.String drpAllocation,
           int drpIdle,
           int drpMaxResc,
           int drpMaxTime,
           int drpMinResc,
           int drpMinTime,
           int maxBundling,
           boolean piggyBacking,
           boolean preFetching,
           java.lang.String scheduler,
           boolean valid) {
           this.maxBundling = maxBundling;
           this.piggyBacking = piggyBacking;
           this.preFetching = preFetching;
           this.drpAllocation = drpAllocation;
           this.drpMinResc = drpMinResc;
           this.drpMaxResc = drpMaxResc;
           this.drpMinTime = drpMinTime;
           this.drpMaxTime = drpMaxTime;
           this.drpIdle = drpIdle;
           this.scheduler = scheduler;
           this.dataCaching = dataCaching;
           this.valid = valid;
    }


    /**
     * Gets the maxBundling value for this MonitorConfigResponse.
     * 
     * @return maxBundling
     */
    public int getMaxBundling() {
        return maxBundling;
    }


    /**
     * Sets the maxBundling value for this MonitorConfigResponse.
     * 
     * @param maxBundling
     */
    public void setMaxBundling(int maxBundling) {
        this.maxBundling = maxBundling;
    }


    /**
     * Gets the piggyBacking value for this MonitorConfigResponse.
     * 
     * @return piggyBacking
     */
    public boolean isPiggyBacking() {
        return piggyBacking;
    }


    /**
     * Sets the piggyBacking value for this MonitorConfigResponse.
     * 
     * @param piggyBacking
     */
    public void setPiggyBacking(boolean piggyBacking) {
        this.piggyBacking = piggyBacking;
    }


    /**
     * Gets the preFetching value for this MonitorConfigResponse.
     * 
     * @return preFetching
     */
    public boolean isPreFetching() {
        return preFetching;
    }


    /**
     * Sets the preFetching value for this MonitorConfigResponse.
     * 
     * @param preFetching
     */
    public void setPreFetching(boolean preFetching) {
        this.preFetching = preFetching;
    }


    /**
     * Gets the drpAllocation value for this MonitorConfigResponse.
     * 
     * @return drpAllocation
     */
    public java.lang.String getDrpAllocation() {
        return drpAllocation;
    }


    /**
     * Sets the drpAllocation value for this MonitorConfigResponse.
     * 
     * @param drpAllocation
     */
    public void setDrpAllocation(java.lang.String drpAllocation) {
        this.drpAllocation = drpAllocation;
    }


    /**
     * Gets the drpMinResc value for this MonitorConfigResponse.
     * 
     * @return drpMinResc
     */
    public int getDrpMinResc() {
        return drpMinResc;
    }


    /**
     * Sets the drpMinResc value for this MonitorConfigResponse.
     * 
     * @param drpMinResc
     */
    public void setDrpMinResc(int drpMinResc) {
        this.drpMinResc = drpMinResc;
    }


    /**
     * Gets the drpMaxResc value for this MonitorConfigResponse.
     * 
     * @return drpMaxResc
     */
    public int getDrpMaxResc() {
        return drpMaxResc;
    }


    /**
     * Sets the drpMaxResc value for this MonitorConfigResponse.
     * 
     * @param drpMaxResc
     */
    public void setDrpMaxResc(int drpMaxResc) {
        this.drpMaxResc = drpMaxResc;
    }


    /**
     * Gets the drpMinTime value for this MonitorConfigResponse.
     * 
     * @return drpMinTime
     */
    public int getDrpMinTime() {
        return drpMinTime;
    }


    /**
     * Sets the drpMinTime value for this MonitorConfigResponse.
     * 
     * @param drpMinTime
     */
    public void setDrpMinTime(int drpMinTime) {
        this.drpMinTime = drpMinTime;
    }


    /**
     * Gets the drpMaxTime value for this MonitorConfigResponse.
     * 
     * @return drpMaxTime
     */
    public int getDrpMaxTime() {
        return drpMaxTime;
    }


    /**
     * Sets the drpMaxTime value for this MonitorConfigResponse.
     * 
     * @param drpMaxTime
     */
    public void setDrpMaxTime(int drpMaxTime) {
        this.drpMaxTime = drpMaxTime;
    }


    /**
     * Gets the drpIdle value for this MonitorConfigResponse.
     * 
     * @return drpIdle
     */
    public int getDrpIdle() {
        return drpIdle;
    }


    /**
     * Sets the drpIdle value for this MonitorConfigResponse.
     * 
     * @param drpIdle
     */
    public void setDrpIdle(int drpIdle) {
        this.drpIdle = drpIdle;
    }


    /**
     * Gets the scheduler value for this MonitorConfigResponse.
     * 
     * @return scheduler
     */
    public java.lang.String getScheduler() {
        return scheduler;
    }


    /**
     * Sets the scheduler value for this MonitorConfigResponse.
     * 
     * @param scheduler
     */
    public void setScheduler(java.lang.String scheduler) {
        this.scheduler = scheduler;
    }


    /**
     * Gets the dataCaching value for this MonitorConfigResponse.
     * 
     * @return dataCaching
     */
    public boolean isDataCaching() {
        return dataCaching;
    }


    /**
     * Sets the dataCaching value for this MonitorConfigResponse.
     * 
     * @param dataCaching
     */
    public void setDataCaching(boolean dataCaching) {
        this.dataCaching = dataCaching;
    }


    /**
     * Gets the valid value for this MonitorConfigResponse.
     * 
     * @return valid
     */
    public boolean isValid() {
        return valid;
    }


    /**
     * Sets the valid value for this MonitorConfigResponse.
     * 
     * @param valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MonitorConfigResponse)) return false;
        MonitorConfigResponse other = (MonitorConfigResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.maxBundling == other.getMaxBundling() &&
            this.piggyBacking == other.isPiggyBacking() &&
            this.preFetching == other.isPreFetching() &&
            ((this.drpAllocation==null && other.getDrpAllocation()==null) || 
             (this.drpAllocation!=null &&
              this.drpAllocation.equals(other.getDrpAllocation()))) &&
            this.drpMinResc == other.getDrpMinResc() &&
            this.drpMaxResc == other.getDrpMaxResc() &&
            this.drpMinTime == other.getDrpMinTime() &&
            this.drpMaxTime == other.getDrpMaxTime() &&
            this.drpIdle == other.getDrpIdle() &&
            ((this.scheduler==null && other.getScheduler()==null) || 
             (this.scheduler!=null &&
              this.scheduler.equals(other.getScheduler()))) &&
            this.dataCaching == other.isDataCaching() &&
            this.valid == other.isValid();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getMaxBundling();
        _hashCode += (isPiggyBacking() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isPreFetching() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getDrpAllocation() != null) {
            _hashCode += getDrpAllocation().hashCode();
        }
        _hashCode += getDrpMinResc();
        _hashCode += getDrpMaxResc();
        _hashCode += getDrpMinTime();
        _hashCode += getDrpMaxTime();
        _hashCode += getDrpIdle();
        if (getScheduler() != null) {
            _hashCode += getScheduler().hashCode();
        }
        _hashCode += (isDataCaching() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isValid() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MonitorConfigResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">MonitorConfigResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxBundling");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxBundling"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("piggyBacking");
        elemField.setXmlName(new javax.xml.namespace.QName("", "piggyBacking"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preFetching");
        elemField.setXmlName(new javax.xml.namespace.QName("", "preFetching"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpAllocation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpAllocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpMinResc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpMinResc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpMaxResc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpMaxResc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpMinTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpMinTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpMaxTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpMaxTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpIdle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpIdle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scheduler");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scheduler"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataCaching");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataCaching"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
