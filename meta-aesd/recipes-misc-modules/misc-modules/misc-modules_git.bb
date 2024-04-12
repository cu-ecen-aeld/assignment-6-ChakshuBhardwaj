# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
#LICENSE = "Unknown"

#LICENSE = "GPL-2.0-only"
#LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

inherit module
inherit update-rc.d

MODULE_DIR = "/lib/modules/5.15.124-yocto-standard/extra"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"



SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-ChakshuBhardwaj.git;protocol=https;branch=master"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "da434f2d54fb235885a6d4f82814d600b64bbe1d"         

S = "${WORKDIR}/git"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
RPROVIDES:${PN} += "kernel-module-faulty"

FILES:${PN} += "${MODULE_DIR}/module_load"
FILES:${PN} += "${MODULE_DIR}/module_unload"
FILES:${PN} += "${sysconfdir}/init.d/module_start_stop.sh"

INITSCRIPT_PACKAGES="${PN}"
INITSCRIPT_NAME:${PN}="module_start_stop.sh"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
    
    install -d ${D}${MODULE_DIR}
	install -m 0777 ${S}/misc-modules/module_load ${D}${MODULE_DIR}/
    install -m 0777 ${S}/misc-modules/module_unload ${D}${MODULE_DIR}/
    install -m 0777 ${S}/misc-modules/faulty.ko ${D}${MODULE_DIR}/
    install -m 0777 ${S}/misc-modules/hello.ko ${D}${MODULE_DIR}/

    

	install -d ${D}${sysconfdir}/init.d/
	install -m 0777 ${S}/misc-modules/module_start_stop.sh ${D}${sysconfdir}/init.d/
	
}





