/**
 * DataFiles.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class DataFiles  implements java.io.Serializable {
    private java.lang.String[] logicalName;
    private java.lang.String[] fileURL;
    private int[] fileSize;
    private org.globus.GenericPortal.stubs.GPService_instance.DataCache[] dataCache;

    public DataFiles() {
    }

    public DataFiles(
           org.globus.GenericPortal.stubs.GPService_instance.DataCache[] dataCache,
           int[] fileSize,
           java.lang.String[] fileURL,
           java.lang.String[] logicalName) {
           this.logicalName = logicalName;
           this.fileURL = fileURL;
           this.fileSize = fileSize;
           this.dataCache = dataCache;
    }


    /**
     * Gets the logicalName value for this DataFiles.
     * 
     * @return logicalName
     */
    public java.lang.String[] getLogicalName() {
        return logicalName;
    }


    /**
     * Sets the logicalName value for this DataFiles.
     * 
     * @param logicalName
     */
    public void setLogicalName(java.lang.String[] logicalName) {
        this.logicalName = logicalName;
    }

    public java.lang.String getLogicalName(int i) {
        return this.logicalName[i];
    }

    public void setLogicalName(int i, java.lang.String _value) {
        this.logicalName[i] = _value;
    }


    /**
     * Gets the fileURL value for this DataFiles.
     * 
     * @return fileURL
     */
    public java.lang.String[] getFileURL() {
        return fileURL;
    }


    /**
     * Sets the fileURL value for this DataFiles.
     * 
     * @param fileURL
     */
    public void setFileURL(java.lang.String[] fileURL) {
        this.fileURL = fileURL;
    }

    public java.lang.String getFileURL(int i) {
        return this.fileURL[i];
    }

    public void setFileURL(int i, java.lang.String _value) {
        this.fileURL[i] = _value;
    }


    /**
     * Gets the fileSize value for this DataFiles.
     * 
     * @return fileSize
     */
    public int[] getFileSize() {
        return fileSize;
    }


    /**
     * Sets the fileSize value for this DataFiles.
     * 
     * @param fileSize
     */
    public void setFileSize(int[] fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileSize(int i) {
        return this.fileSize[i];
    }

    public void setFileSize(int i, int _value) {
        this.fileSize[i] = _value;
    }


    /**
     * Gets the dataCache value for this DataFiles.
     * 
     * @return dataCache
     */
    public org.globus.GenericPortal.stubs.GPService_instance.DataCache[] getDataCache() {
        return dataCache;
    }


    /**
     * Sets the dataCache value for this DataFiles.
     * 
     * @param dataCache
     */
    public void setDataCache(org.globus.GenericPortal.stubs.GPService_instance.DataCache[] dataCache) {
        this.dataCache = dataCache;
    }

    public org.globus.GenericPortal.stubs.GPService_instance.DataCache getDataCache(int i) {
        return this.dataCache[i];
    }

    public void setDataCache(int i, org.globus.GenericPortal.stubs.GPService_instance.DataCache _value) {
        this.dataCache[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DataFiles)) return false;
        DataFiles other = (DataFiles) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.logicalName==null && other.getLogicalName()==null) || 
             (this.logicalName!=null &&
              java.util.Arrays.equals(this.logicalName, other.getLogicalName()))) &&
            ((this.fileURL==null && other.getFileURL()==null) || 
             (this.fileURL!=null &&
              java.util.Arrays.equals(this.fileURL, other.getFileURL()))) &&
            ((this.fileSize==null && other.getFileSize()==null) || 
             (this.fileSize!=null &&
              java.util.Arrays.equals(this.fileSize, other.getFileSize()))) &&
            ((this.dataCache==null && other.getDataCache()==null) || 
             (this.dataCache!=null &&
              java.util.Arrays.equals(this.dataCache, other.getDataCache())));
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
        if (getLogicalName() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLogicalName());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLogicalName(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFileURL() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFileURL());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFileURL(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFileSize() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFileSize());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFileSize(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDataCache() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDataCache());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDataCache(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DataFiles.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "DataFiles"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logicalName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "logicalName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileURL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fileURL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileSize");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fileSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataCache");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataCache"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "DataCache"));
        elemField.setMinOccurs(0);
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
