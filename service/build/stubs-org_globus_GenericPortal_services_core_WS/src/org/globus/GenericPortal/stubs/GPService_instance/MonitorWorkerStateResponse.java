/**
 * MonitorWorkerStateResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class MonitorWorkerStateResponse  implements java.io.Serializable {
    private java.lang.String[] freeWorkers;
    private int freeWorkerNum;
    private java.lang.String[] pendWorkers;
    private int pendWorkerNum;
    private java.lang.String[] busyWorkers;
    private int busyWorkerNum;

    public MonitorWorkerStateResponse() {
    }

    public MonitorWorkerStateResponse(
           int busyWorkerNum,
           java.lang.String[] busyWorkers,
           int freeWorkerNum,
           java.lang.String[] freeWorkers,
           int pendWorkerNum,
           java.lang.String[] pendWorkers) {
           this.freeWorkers = freeWorkers;
           this.freeWorkerNum = freeWorkerNum;
           this.pendWorkers = pendWorkers;
           this.pendWorkerNum = pendWorkerNum;
           this.busyWorkers = busyWorkers;
           this.busyWorkerNum = busyWorkerNum;
    }


    /**
     * Gets the freeWorkers value for this MonitorWorkerStateResponse.
     * 
     * @return freeWorkers
     */
    public java.lang.String[] getFreeWorkers() {
        return freeWorkers;
    }


    /**
     * Sets the freeWorkers value for this MonitorWorkerStateResponse.
     * 
     * @param freeWorkers
     */
    public void setFreeWorkers(java.lang.String[] freeWorkers) {
        this.freeWorkers = freeWorkers;
    }

    public java.lang.String getFreeWorkers(int i) {
        return this.freeWorkers[i];
    }

    public void setFreeWorkers(int i, java.lang.String _value) {
        this.freeWorkers[i] = _value;
    }


    /**
     * Gets the freeWorkerNum value for this MonitorWorkerStateResponse.
     * 
     * @return freeWorkerNum
     */
    public int getFreeWorkerNum() {
        return freeWorkerNum;
    }


    /**
     * Sets the freeWorkerNum value for this MonitorWorkerStateResponse.
     * 
     * @param freeWorkerNum
     */
    public void setFreeWorkerNum(int freeWorkerNum) {
        this.freeWorkerNum = freeWorkerNum;
    }


    /**
     * Gets the pendWorkers value for this MonitorWorkerStateResponse.
     * 
     * @return pendWorkers
     */
    public java.lang.String[] getPendWorkers() {
        return pendWorkers;
    }


    /**
     * Sets the pendWorkers value for this MonitorWorkerStateResponse.
     * 
     * @param pendWorkers
     */
    public void setPendWorkers(java.lang.String[] pendWorkers) {
        this.pendWorkers = pendWorkers;
    }

    public java.lang.String getPendWorkers(int i) {
        return this.pendWorkers[i];
    }

    public void setPendWorkers(int i, java.lang.String _value) {
        this.pendWorkers[i] = _value;
    }


    /**
     * Gets the pendWorkerNum value for this MonitorWorkerStateResponse.
     * 
     * @return pendWorkerNum
     */
    public int getPendWorkerNum() {
        return pendWorkerNum;
    }


    /**
     * Sets the pendWorkerNum value for this MonitorWorkerStateResponse.
     * 
     * @param pendWorkerNum
     */
    public void setPendWorkerNum(int pendWorkerNum) {
        this.pendWorkerNum = pendWorkerNum;
    }


    /**
     * Gets the busyWorkers value for this MonitorWorkerStateResponse.
     * 
     * @return busyWorkers
     */
    public java.lang.String[] getBusyWorkers() {
        return busyWorkers;
    }


    /**
     * Sets the busyWorkers value for this MonitorWorkerStateResponse.
     * 
     * @param busyWorkers
     */
    public void setBusyWorkers(java.lang.String[] busyWorkers) {
        this.busyWorkers = busyWorkers;
    }

    public java.lang.String getBusyWorkers(int i) {
        return this.busyWorkers[i];
    }

    public void setBusyWorkers(int i, java.lang.String _value) {
        this.busyWorkers[i] = _value;
    }


    /**
     * Gets the busyWorkerNum value for this MonitorWorkerStateResponse.
     * 
     * @return busyWorkerNum
     */
    public int getBusyWorkerNum() {
        return busyWorkerNum;
    }


    /**
     * Sets the busyWorkerNum value for this MonitorWorkerStateResponse.
     * 
     * @param busyWorkerNum
     */
    public void setBusyWorkerNum(int busyWorkerNum) {
        this.busyWorkerNum = busyWorkerNum;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MonitorWorkerStateResponse)) return false;
        MonitorWorkerStateResponse other = (MonitorWorkerStateResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.freeWorkers==null && other.getFreeWorkers()==null) || 
             (this.freeWorkers!=null &&
              java.util.Arrays.equals(this.freeWorkers, other.getFreeWorkers()))) &&
            this.freeWorkerNum == other.getFreeWorkerNum() &&
            ((this.pendWorkers==null && other.getPendWorkers()==null) || 
             (this.pendWorkers!=null &&
              java.util.Arrays.equals(this.pendWorkers, other.getPendWorkers()))) &&
            this.pendWorkerNum == other.getPendWorkerNum() &&
            ((this.busyWorkers==null && other.getBusyWorkers()==null) || 
             (this.busyWorkers!=null &&
              java.util.Arrays.equals(this.busyWorkers, other.getBusyWorkers()))) &&
            this.busyWorkerNum == other.getBusyWorkerNum();
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
        if (getFreeWorkers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFreeWorkers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFreeWorkers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getFreeWorkerNum();
        if (getPendWorkers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPendWorkers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPendWorkers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getPendWorkerNum();
        if (getBusyWorkers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBusyWorkers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBusyWorkers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getBusyWorkerNum();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MonitorWorkerStateResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">MonitorWorkerStateResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freeWorkers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "freeWorkers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freeWorkerNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "freeWorkerNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pendWorkers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pendWorkers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pendWorkerNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pendWorkerNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("busyWorkers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "busyWorkers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("busyWorkerNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "busyWorkerNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
