/**
 * GPResourceProperties.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class GPResourceProperties  implements java.io.Serializable {
    private int numJobs;
    private java.lang.String state;
    private int numWorkers;
    private int numUserResults;
    private int numWorkerResults;
    private java.util.Calendar terminationTime;
    private java.util.Calendar currentTime;

    public GPResourceProperties() {
    }

    public GPResourceProperties(
           java.util.Calendar currentTime,
           int numJobs,
           int numUserResults,
           int numWorkerResults,
           int numWorkers,
           java.lang.String state,
           java.util.Calendar terminationTime) {
           this.numJobs = numJobs;
           this.state = state;
           this.numWorkers = numWorkers;
           this.numUserResults = numUserResults;
           this.numWorkerResults = numWorkerResults;
           this.terminationTime = terminationTime;
           this.currentTime = currentTime;
    }


    /**
     * Gets the numJobs value for this GPResourceProperties.
     * 
     * @return numJobs
     */
    public int getNumJobs() {
        return numJobs;
    }


    /**
     * Sets the numJobs value for this GPResourceProperties.
     * 
     * @param numJobs
     */
    public void setNumJobs(int numJobs) {
        this.numJobs = numJobs;
    }


    /**
     * Gets the state value for this GPResourceProperties.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this GPResourceProperties.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the numWorkers value for this GPResourceProperties.
     * 
     * @return numWorkers
     */
    public int getNumWorkers() {
        return numWorkers;
    }


    /**
     * Sets the numWorkers value for this GPResourceProperties.
     * 
     * @param numWorkers
     */
    public void setNumWorkers(int numWorkers) {
        this.numWorkers = numWorkers;
    }


    /**
     * Gets the numUserResults value for this GPResourceProperties.
     * 
     * @return numUserResults
     */
    public int getNumUserResults() {
        return numUserResults;
    }


    /**
     * Sets the numUserResults value for this GPResourceProperties.
     * 
     * @param numUserResults
     */
    public void setNumUserResults(int numUserResults) {
        this.numUserResults = numUserResults;
    }


    /**
     * Gets the numWorkerResults value for this GPResourceProperties.
     * 
     * @return numWorkerResults
     */
    public int getNumWorkerResults() {
        return numWorkerResults;
    }


    /**
     * Sets the numWorkerResults value for this GPResourceProperties.
     * 
     * @param numWorkerResults
     */
    public void setNumWorkerResults(int numWorkerResults) {
        this.numWorkerResults = numWorkerResults;
    }


    /**
     * Gets the terminationTime value for this GPResourceProperties.
     * 
     * @return terminationTime
     */
    public java.util.Calendar getTerminationTime() {
        return terminationTime;
    }


    /**
     * Sets the terminationTime value for this GPResourceProperties.
     * 
     * @param terminationTime
     */
    public void setTerminationTime(java.util.Calendar terminationTime) {
        this.terminationTime = terminationTime;
    }


    /**
     * Gets the currentTime value for this GPResourceProperties.
     * 
     * @return currentTime
     */
    public java.util.Calendar getCurrentTime() {
        return currentTime;
    }


    /**
     * Sets the currentTime value for this GPResourceProperties.
     * 
     * @param currentTime
     */
    public void setCurrentTime(java.util.Calendar currentTime) {
        this.currentTime = currentTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GPResourceProperties)) return false;
        GPResourceProperties other = (GPResourceProperties) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.numJobs == other.getNumJobs() &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            this.numWorkers == other.getNumWorkers() &&
            this.numUserResults == other.getNumUserResults() &&
            this.numWorkerResults == other.getNumWorkerResults() &&
            ((this.terminationTime==null && other.getTerminationTime()==null) || 
             (this.terminationTime!=null &&
              this.terminationTime.equals(other.getTerminationTime()))) &&
            ((this.currentTime==null && other.getCurrentTime()==null) || 
             (this.currentTime!=null &&
              this.currentTime.equals(other.getCurrentTime())));
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
        _hashCode += getNumJobs();
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        _hashCode += getNumWorkers();
        _hashCode += getNumUserResults();
        _hashCode += getNumWorkerResults();
        if (getTerminationTime() != null) {
            _hashCode += getTerminationTime().hashCode();
        }
        if (getCurrentTime() != null) {
            _hashCode += getCurrentTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GPResourceProperties.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">GPResourceProperties"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numJobs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "NumJobs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numWorkers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "NumWorkers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numUserResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "NumUserResults"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numWorkerResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "NumWorkerResults"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminationTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "TerminationTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "CurrentTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
