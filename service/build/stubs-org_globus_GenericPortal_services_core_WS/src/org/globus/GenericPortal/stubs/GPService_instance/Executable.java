/**
 * Executable.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class Executable  implements java.io.Serializable {
    private java.lang.String id;
    private java.lang.String notification;
    private java.lang.String command;
    private java.lang.String[] arguements;
    private java.lang.String[] environment;
    private java.lang.String directory;
    private int wallTime;
    private boolean dataCaching;
    private org.globus.GenericPortal.stubs.GPService_instance.DataFiles inputData;
    private org.globus.GenericPortal.stubs.GPService_instance.DataFiles outputData;

    public Executable() {
    }

    public Executable(
           java.lang.String[] arguements,
           java.lang.String command,
           boolean dataCaching,
           java.lang.String directory,
           java.lang.String[] environment,
           java.lang.String id,
           org.globus.GenericPortal.stubs.GPService_instance.DataFiles inputData,
           java.lang.String notification,
           org.globus.GenericPortal.stubs.GPService_instance.DataFiles outputData,
           int wallTime) {
           this.id = id;
           this.notification = notification;
           this.command = command;
           this.arguements = arguements;
           this.environment = environment;
           this.directory = directory;
           this.wallTime = wallTime;
           this.dataCaching = dataCaching;
           this.inputData = inputData;
           this.outputData = outputData;
    }


    /**
     * Gets the id value for this Executable.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this Executable.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the notification value for this Executable.
     * 
     * @return notification
     */
    public java.lang.String getNotification() {
        return notification;
    }


    /**
     * Sets the notification value for this Executable.
     * 
     * @param notification
     */
    public void setNotification(java.lang.String notification) {
        this.notification = notification;
    }


    /**
     * Gets the command value for this Executable.
     * 
     * @return command
     */
    public java.lang.String getCommand() {
        return command;
    }


    /**
     * Sets the command value for this Executable.
     * 
     * @param command
     */
    public void setCommand(java.lang.String command) {
        this.command = command;
    }


    /**
     * Gets the arguements value for this Executable.
     * 
     * @return arguements
     */
    public java.lang.String[] getArguements() {
        return arguements;
    }


    /**
     * Sets the arguements value for this Executable.
     * 
     * @param arguements
     */
    public void setArguements(java.lang.String[] arguements) {
        this.arguements = arguements;
    }

    public java.lang.String getArguements(int i) {
        return this.arguements[i];
    }

    public void setArguements(int i, java.lang.String _value) {
        this.arguements[i] = _value;
    }


    /**
     * Gets the environment value for this Executable.
     * 
     * @return environment
     */
    public java.lang.String[] getEnvironment() {
        return environment;
    }


    /**
     * Sets the environment value for this Executable.
     * 
     * @param environment
     */
    public void setEnvironment(java.lang.String[] environment) {
        this.environment = environment;
    }

    public java.lang.String getEnvironment(int i) {
        return this.environment[i];
    }

    public void setEnvironment(int i, java.lang.String _value) {
        this.environment[i] = _value;
    }


    /**
     * Gets the directory value for this Executable.
     * 
     * @return directory
     */
    public java.lang.String getDirectory() {
        return directory;
    }


    /**
     * Sets the directory value for this Executable.
     * 
     * @param directory
     */
    public void setDirectory(java.lang.String directory) {
        this.directory = directory;
    }


    /**
     * Gets the wallTime value for this Executable.
     * 
     * @return wallTime
     */
    public int getWallTime() {
        return wallTime;
    }


    /**
     * Sets the wallTime value for this Executable.
     * 
     * @param wallTime
     */
    public void setWallTime(int wallTime) {
        this.wallTime = wallTime;
    }


    /**
     * Gets the dataCaching value for this Executable.
     * 
     * @return dataCaching
     */
    public boolean isDataCaching() {
        return dataCaching;
    }


    /**
     * Sets the dataCaching value for this Executable.
     * 
     * @param dataCaching
     */
    public void setDataCaching(boolean dataCaching) {
        this.dataCaching = dataCaching;
    }


    /**
     * Gets the inputData value for this Executable.
     * 
     * @return inputData
     */
    public org.globus.GenericPortal.stubs.GPService_instance.DataFiles getInputData() {
        return inputData;
    }


    /**
     * Sets the inputData value for this Executable.
     * 
     * @param inputData
     */
    public void setInputData(org.globus.GenericPortal.stubs.GPService_instance.DataFiles inputData) {
        this.inputData = inputData;
    }


    /**
     * Gets the outputData value for this Executable.
     * 
     * @return outputData
     */
    public org.globus.GenericPortal.stubs.GPService_instance.DataFiles getOutputData() {
        return outputData;
    }


    /**
     * Sets the outputData value for this Executable.
     * 
     * @param outputData
     */
    public void setOutputData(org.globus.GenericPortal.stubs.GPService_instance.DataFiles outputData) {
        this.outputData = outputData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Executable)) return false;
        Executable other = (Executable) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.notification==null && other.getNotification()==null) || 
             (this.notification!=null &&
              this.notification.equals(other.getNotification()))) &&
            ((this.command==null && other.getCommand()==null) || 
             (this.command!=null &&
              this.command.equals(other.getCommand()))) &&
            ((this.arguements==null && other.getArguements()==null) || 
             (this.arguements!=null &&
              java.util.Arrays.equals(this.arguements, other.getArguements()))) &&
            ((this.environment==null && other.getEnvironment()==null) || 
             (this.environment!=null &&
              java.util.Arrays.equals(this.environment, other.getEnvironment()))) &&
            ((this.directory==null && other.getDirectory()==null) || 
             (this.directory!=null &&
              this.directory.equals(other.getDirectory()))) &&
            this.wallTime == other.getWallTime() &&
            this.dataCaching == other.isDataCaching() &&
            ((this.inputData==null && other.getInputData()==null) || 
             (this.inputData!=null &&
              this.inputData.equals(other.getInputData()))) &&
            ((this.outputData==null && other.getOutputData()==null) || 
             (this.outputData!=null &&
              this.outputData.equals(other.getOutputData())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getNotification() != null) {
            _hashCode += getNotification().hashCode();
        }
        if (getCommand() != null) {
            _hashCode += getCommand().hashCode();
        }
        if (getArguements() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getArguements());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getArguements(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEnvironment() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEnvironment());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEnvironment(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDirectory() != null) {
            _hashCode += getDirectory().hashCode();
        }
        _hashCode += getWallTime();
        _hashCode += (isDataCaching() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getInputData() != null) {
            _hashCode += getInputData().hashCode();
        }
        if (getOutputData() != null) {
            _hashCode += getOutputData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Executable.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "Executable"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notification");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("command");
        elemField.setXmlName(new javax.xml.namespace.QName("", "command"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arguements");
        elemField.setXmlName(new javax.xml.namespace.QName("", "arguements"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("environment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "environment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("directory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "directory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wallTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wallTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataCaching");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataCaching"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inputData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inputData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "DataFiles"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "outputData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "DataFiles"));
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
