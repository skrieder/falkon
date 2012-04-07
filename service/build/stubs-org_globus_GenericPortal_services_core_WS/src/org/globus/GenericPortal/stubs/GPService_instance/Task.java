/**
 * Task.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class Task  implements java.io.Serializable {
    private org.globus.GenericPortal.stubs.GPService_instance.Executable executable;
    private int exitCode;
    private java.lang.String stdout;
    private java.lang.String stderr;
    private boolean captureStdout;
    private boolean captureStderr;

    public Task() {
    }

    public Task(
           boolean captureStderr,
           boolean captureStdout,
           org.globus.GenericPortal.stubs.GPService_instance.Executable executable,
           int exitCode,
           java.lang.String stderr,
           java.lang.String stdout) {
           this.executable = executable;
           this.exitCode = exitCode;
           this.stdout = stdout;
           this.stderr = stderr;
           this.captureStdout = captureStdout;
           this.captureStderr = captureStderr;
    }


    /**
     * Gets the executable value for this Task.
     * 
     * @return executable
     */
    public org.globus.GenericPortal.stubs.GPService_instance.Executable getExecutable() {
        return executable;
    }


    /**
     * Sets the executable value for this Task.
     * 
     * @param executable
     */
    public void setExecutable(org.globus.GenericPortal.stubs.GPService_instance.Executable executable) {
        this.executable = executable;
    }


    /**
     * Gets the exitCode value for this Task.
     * 
     * @return exitCode
     */
    public int getExitCode() {
        return exitCode;
    }


    /**
     * Sets the exitCode value for this Task.
     * 
     * @param exitCode
     */
    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }


    /**
     * Gets the stdout value for this Task.
     * 
     * @return stdout
     */
    public java.lang.String getStdout() {
        return stdout;
    }


    /**
     * Sets the stdout value for this Task.
     * 
     * @param stdout
     */
    public void setStdout(java.lang.String stdout) {
        this.stdout = stdout;
    }


    /**
     * Gets the stderr value for this Task.
     * 
     * @return stderr
     */
    public java.lang.String getStderr() {
        return stderr;
    }


    /**
     * Sets the stderr value for this Task.
     * 
     * @param stderr
     */
    public void setStderr(java.lang.String stderr) {
        this.stderr = stderr;
    }


    /**
     * Gets the captureStdout value for this Task.
     * 
     * @return captureStdout
     */
    public boolean isCaptureStdout() {
        return captureStdout;
    }


    /**
     * Sets the captureStdout value for this Task.
     * 
     * @param captureStdout
     */
    public void setCaptureStdout(boolean captureStdout) {
        this.captureStdout = captureStdout;
    }


    /**
     * Gets the captureStderr value for this Task.
     * 
     * @return captureStderr
     */
    public boolean isCaptureStderr() {
        return captureStderr;
    }


    /**
     * Sets the captureStderr value for this Task.
     * 
     * @param captureStderr
     */
    public void setCaptureStderr(boolean captureStderr) {
        this.captureStderr = captureStderr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Task)) return false;
        Task other = (Task) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.executable==null && other.getExecutable()==null) || 
             (this.executable!=null &&
              this.executable.equals(other.getExecutable()))) &&
            this.exitCode == other.getExitCode() &&
            ((this.stdout==null && other.getStdout()==null) || 
             (this.stdout!=null &&
              this.stdout.equals(other.getStdout()))) &&
            ((this.stderr==null && other.getStderr()==null) || 
             (this.stderr!=null &&
              this.stderr.equals(other.getStderr()))) &&
            this.captureStdout == other.isCaptureStdout() &&
            this.captureStderr == other.isCaptureStderr();
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
        if (getExecutable() != null) {
            _hashCode += getExecutable().hashCode();
        }
        _hashCode += getExitCode();
        if (getStdout() != null) {
            _hashCode += getStdout().hashCode();
        }
        if (getStderr() != null) {
            _hashCode += getStderr().hashCode();
        }
        _hashCode += (isCaptureStdout() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isCaptureStderr() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Task.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "Task"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("executable");
        elemField.setXmlName(new javax.xml.namespace.QName("", "executable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "Executable"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exitCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "exitCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stdout");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stdout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stderr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stderr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("captureStdout");
        elemField.setXmlName(new javax.xml.namespace.QName("", "captureStdout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("captureStderr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "captureStderr"));
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
