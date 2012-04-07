#!/usr/bin/python

#####
#
# Build script for GT4 Web services
# GSBT - Globus Service Build Tools
# http://gsbt.sourceforge.net/
#
# Version 0.2.4
# Full changelog available at the GSBT website
#
# Copyright (c) 2004 Borja Sotomayor
# The Globus Service Build Tools are available for use and redistribution 
# under the terms of a BSD license available at http://gsbt.sourceforge.net/license.html
#
######


import os
import sys
import string


def printUsage():

	print ""
	print "Usage:"
	print "%s -d <service_dir> -s <schema_file> [-fs <factory_schema_file>] [-t <target>] [--debug]" % sys.argv[0]
	print "%s <service_id> [target] [--debug]" % sys.argv[0]
	print "%s -h" % sys.argv[0]
	print ""
	print "<service_dir> is the directory that contains all the implementation and deployment files:"
	print "\t<service_dir>/deploy-server.wsdd         Deployment file (mandatory)"
	print "\t<service_dir>/deploy-jndi-config.wsdd    JNDI deployment file (mandatory)"
	print "\t<service_dir>/impl/*.java                Java implementation files (mandatory)"
	print "\t<service_dir>/etc/*.xml                  Configuration files (optional)"
	print ""
	print "<schema_file> is the WSDL file with the service's interface description"
	print ""
	print "<factory_schema_file> is an optional parameter. If your service is a "
	print "factory/instance service, you can use this parameter to specify the factory's"
	print "schema file."
	print ""
	print "<target> is an optional parameter to control what Ant builds. Valid values are"
	print "\t all     Builds everything (default)"
	print "\t stubs   Generates the stubs (but doesn't compile them)."
	print "\t compileStubs   Generates and compiles the stubs."
	print ""
	print "--debug provides detailed information of what the build script is doing."
	print ""
	print "The script offers a shorthand way of building services through the <service_id>"
	print "parameter. It allows you to build services without having to type the"
	print "service directory and schema file every time. You must have a 'build.mappings'"
	print "file in the same directory as the build script, with one line for each service"
	print "using the following format:"
	print "\t<service_id>,<service_dir>,<schema_file>,<factory_schema_file>"
	print ""
	print "\t (the <factory_schema_file> is optional)"


# Builds service
# Parameter 1: Service base directory
# Parameter 2: Schema file (WSDL file)
# Parameter 3: Factory schema file (WSDL file)
# Parameter 4: Ant build target
# Parameter 5: Additional ant arguments
# Parameter 6: Is this a dry run?
def build(serviceDir, schemaPath, factoryschemaPath, target, antArgs, dryrun):

	(schemaDir, schemaFile) = os.path.split(schemaPath)
	(interface, ext) = os.path.splitext(schemaFile)
		
	schemaDirSplit = schemaDir.split(os.path.sep)
	schemaDir = os.path.sep.join(schemaDirSplit[1:])

	package = serviceDir.replace("/", ".") # UNIX users
	package = package.replace("\\", ".")     # Windows users
	serviceName = schemaDirSplit.pop()
	garFilename = serviceDir.replace("/", "_")   # UNIX users
	garFilename = garFilename.replace("\\", "_") # Windows users

	command = "ant" + \
		" " + antArgs + \
		" " + target + \
		" -Dpackage=" + package + \
		" -Dinterface.name=" + interface + \
		" -Dpackage.dir=" + serviceDir + \
		" -Dschema.path=" + schemaDir + \
		" -Dservice.name=" + serviceName + \
		" -Dgar.filename=" + garFilename

	if factoryschemaPath != "":
		(factorySchemaDir, factorySchemaFile) = os.path.split(factoryschemaPath)
		(factoryInterface, ext) = os.path.splitext(factorySchemaFile)
		factorySchemaDirSplit = factorySchemaDir.split(os.path.sep)
		factorySchemaDir = os.path.sep.join(factorySchemaDirSplit[1:])
		command += " -Dfactory.schema.path=" + factorySchemaDir + \
			" -Dfactory.interface.name=" + factoryInterface
	
	if dryrun:
		print command
		exitStatus=0
	else:
		exitStatus=os.system(command)

	return exitStatus


# Checks if the target is valid
# Parameter 1: Target
def validateTarget(target):
	validTargets = ("compileStubs", "stubs", "all")
	if target in validTargets:
		return True
	else:
		return False


# Given a valid service id, fetches that service's base directory and schema file
# The information is deposited in SERVICE_DIR and SCHEMA_PATH
# Parameter 1: Service id
def findBuildParameters(serviceID):
	mapFile = open("build.mappings")
	serviceDir = ""
	schemaPath = ""
	factoryschemaPath = ""
	for line in mapFile:
		fields = line.strip().split(",")
		if fields[0] == serviceID:
			serviceDir = fields[1]
			schemaPath = fields[2]
			if len(fields) == 4:
				factoryschemaPath = fields[3]
	mapFile.close()

	return (serviceDir, schemaPath, factoryschemaPath)


#
# SCRIPT MAIN BODY
#
def main():

	serviceDir = ""
	schemaPath = ""
	factoryschemaPath = ""
	serviceID = ""
	target = ""
	debug = False
	dryrun = False
	
	# Check for GLOBUS_LOCATION
	if os.environ['GLOBUS_LOCATION'] == "":
		print "ERROR: environment variable GLOBUS_LOCATION not defined."
		print "Run %s -h for help." % sys.argv[0]
		sys.exit(1)
		
	if not os.path.isdir(os.path.normpath(os.environ['GLOBUS_LOCATION'])):
		print "ERROR: invalid GLOBUS_LOCATION set: %s" % os.environ['GLOBUS_LOCATION']
		print "Run %s -h for help." % sys.argv[0]
		sys.exit(1)
	
	
	# Get command-line parameters
	numArgs = len(sys.argv)

	if numArgs == 1:
		printUsage()
	        sys.exit(1)

	args = iter(sys.argv[1:])

	for arg in args:
		if arg in ("-d","--dir"):
			serviceDir = args.next()
			continue
		
		if arg in ("-s","--schema"):
			schemaPath = args.next()
			continue

		if arg in ("-fs","--factory-schema"):
			factoryschemaPath = args.next()
			continue

		if arg in ("-t","--target"):
			target = args.next()
			continue

		if arg in ("-h","--help"):
			printUsage()
			sys.exit(0)

		if arg in ("--dry-run"):
			dryrun = True
			continue

		if arg in ("--debug"):
			debug = True
			continue

		# First non-option parameter must be the service id
		if serviceID == "":
			serviceID = arg
		elif target == "":
		# Second non-option parameter must be the target
			target = arg
		else:
		# The script is confused!
			print "ERROR: parameter '%s' not recognized" % arg
			print "Run %s -h for help." % sys.argv[0]

	#
	# VALIDATE PARAMETERS
	#

	# First off, you can't specify a service id *and* a dir/schema pair.
	if serviceID != "" and (serviceDir!="" or schemaPath!=""):
		print "ERROR: Please specify either a service id *or* a service directory and schema file"
		print "       (but not both)"
		print "Run %s -h for help." % sys.argv[0]
		sys.exit(1)


	# If a service id has been specified, we need to fetch SERVICE_DIR and SCHEMA_PATH 
	# (and possibly FACTORY_SCHEMA_PATH) from the build.mappings file
	if serviceID != "":	
		(serviceDir, schemaPath, factoryschemaPath) = findBuildParameters(serviceID)
		if serviceDir == "":
			print "ERROR: Specified service not in mappings file."
			print "Run %s -h for help." % sys.argv[0]
			sys.exit(1)

	# Now we have a SERVICE_DIR and SCHEMA_PATH. First, let's normalize them:
	serviceDir = os.path.normpath(serviceDir)
	schemaPath = os.path.normpath(schemaPath)

	# Now, let's make sure they exist.
	if not os.path.isdir(serviceDir):
		print "%s does not exist or is not a directory." % serviceDir
		print "Run %s -h for help." % sys.argv[0]
		sys.exit(1)

	if not os.path.isfile(schemaPath):
		print "%s does not exist or is not a file." % schemaPath
		print "Run %s -h for help." % sys.argv[0]
		sys.exit(1)

	# If we have a factory schema path, normalize it and make sure it exists:
	if factoryschemaPath != "":
		factoryschemaPath = os.path.normpath(factoryschemaPath)
		if not os.path.isfile(factoryschemaPath):
			print "%s does not exist or is not a file." % factoryschemaPath
			print "Run %s -h for help." % sys.argv[0]
			sys.exit(1)

	# If no target has been specified, the default target will be "all"
	if target == "":
		target = "all"

	# Validate that the target is correct
	if not validateTarget(target):
		print "ERROR: Unknown target."
		print "Run %s -h for help." % sys.argv[0]
		sys.exit(1)


	#
	# CONSTRUCT 'ADDITIONAL PARAMETERS' STRING
	#

	antArgs=""
	# Debug?
	if debug:
		antArgs += " -debug"


	#
	# If you got this far, you deserve to...
	# BUILD!
	#
	exitStatus = build(serviceDir, schemaPath, factoryschemaPath, target, antArgs, dryrun)
	sys.exit(exitStatus)


        
if __name__ == "__main__":

        main()

