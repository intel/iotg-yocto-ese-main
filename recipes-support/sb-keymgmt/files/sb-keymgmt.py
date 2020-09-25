#! /usr/bin/env python

########################################################################################
#  This sample script demonstrates key management for various keys and certificates used
#  in signing secure boot components such as, shim.efi, grub.efi, MokManager.efi, bzImage etc.
#
#  Note:
#  When generating build for production release, this script should be updated to use
#  a Hardware Security Module (HSM) or alternative means MUST be employed to secure
#  private keys and certificates.
#
########################################################################################
#
#  Below is description of various keys and certificates used in Secure Boot
#
#  DB.key    -- Used for signing shim.efi.
#              Certificate for this key is registered in UEFI Secure Boot
#              Signature database (db).
#
#  Yocto.key -- Used for signing GRUB bootloader and bzImage kernel.
#              Certificate for this key is embedded inside shim.efi during build time.
#
#  Shim.key  -- Used for signing MokManager.efi and fallback.efi.
#              Certificate for this key is embedded inside shim.efi during build time.
#
#  Usage Examples:
#  ./ysbkm_debug.py -c gen -kn Yocto.key -kl 2048 -cn Yocto.crt -sn "/CN=Yocto BSP Signing Key/" -vd 365
#  ./ysbkm_debug.py -c sign -kn Yocto.key -cn Yocto.crt -usf grub.efi -sf grub.efi.sign
#
########################################################################################

import os
import subprocess
import argparse
import sys


# Used for generating the keys used for signing secure boot modules.
# If using an HSM, this method can be used to associate the key_name with the
# key stored in HSM.
def gen_key(key_name, key_len):
  print("Generating " + key_name + " Key.")
  cmd = "openssl genrsa -out %s %d" % (key_name, key_len)
  subprocess.check_output(cmd, shell=True)

# Used for getting access to the certificate in PEM format.
# If using an HSM, retrieve the certificate from HSM and convert it into PEM format and provide to caller.
def gen_cert(key_name, cert_name, subj_name, valid_days):
  print("Generating " + cert_name + " Certificate.")
  cmd = "openssl req -new -x509 -key %s -outform PEM -out %s -sha256 -subj \"%s\" -days %d" % (key_name, cert_name, subj_name, valid_days)
  subprocess.check_output(cmd, shell=True)

# Used for signing the efi modules
# If using an HSM, key_name and cert_name will refer to the key and certificate available inside HSM.
def sign_efi(key_name, cert_name, unsigned_file, signed_file):
  print("Signing " + unsigned_file + " efi file.")
  cmd = "sbsign --key %s --cert %s --output %s %s" % (key_name, cert_name, signed_file, unsigned_file)
  subprocess.check_output(cmd, shell=True)

# Used for converting a PEM encoded certificate to DER encoding.
def to_cer(cert_name, cer_name):
  print("Generating " + cer_name + " Certificate.")
  cmd = "openssl x509 -in %s -out %s -outform DER" % (cert_name, cer_name)
  subprocess.check_output(cmd, shell=True)

def main():
  parser = argparse.ArgumentParser()
  parser.add_argument('-c',  '--command', dest='Command', type=str, help='Command: gen OR sign', required = True)
  parser.add_argument('-kn',  '--key_name', dest='KeyName', type=str, help='Key Name file (in PEM format)', required = False)
  parser.add_argument('-kl',  '--key_len', dest='KeyLen', type=int, help='RSA Key length (in bits)', required = False)
  parser.add_argument('-cn',  '--cert_name', dest='CertName', type=str, help='Certificate Name file (in PEM format)', required = False)
  parser.add_argument('-sn',  '--subj_name', dest='SubjName', type=str, help='Subject info string to be embedded in Certificate', required = False)
  parser.add_argument('-vd',  '--valid_days', dest='ValidDays', type=int, help='Validity of Certificate', required = False)
  parser.add_argument('-usf',  '--unsigned_file', dest='UnsignedFile', type=str, help='Unsigned efi file name', required = False)
  parser.add_argument('-sf',  '--signed_file', dest='SignedFile', type=str, help='Signed efi file name', required = False)
  parser.add_argument('-cer',  '--cer_name', dest='CerName', type=str, help='Certificate Name file (in DER format)', required = False)

  args = parser.parse_args()
  print("Command : " + args.Command)

  try:
    if(args.Command == 'gen'):
      gen_key(args.KeyName, args.KeyLen)
      gen_cert(args.KeyName, args.CertName, args.SubjName,args.ValidDays)
    elif(args.Command == 'to_cer'):
      to_cer(args.CertName, args.CerName)
    elif(args.Command == 'sign'):
      sign_efi(args.KeyName, args.CertName, args.UnsignedFile, args.SignedFile)
    else:
      print("Wrong Command !!")

  except subprocess.CalledProcessError as e:
    print(e.cmd + "failed with return code ",  e.returncode)
    return 1
  except:
    return 1

  return 0;

if __name__ == '__main__':
  sys.exit(main())
