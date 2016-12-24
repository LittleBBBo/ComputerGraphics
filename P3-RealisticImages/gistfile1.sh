if ! [ -d /Library/Java/JavaVirtualMachines/1.7.0u6.jdk ]; then
  echo "Downloading OpenJDK 7"
  curl -o /tmp/OpenJDK-7.dmg http://openjdk-osx-build.googlecode.com/files/OpenJDK-OSX-1.7-universal-u-jdk-jdk7u6-b10-20120522.dmg
  
  echo "Attaching DMG image"
  hdiutil attach /tmp/OpenJDK-7.dmg
  echo "Copying JDK into available Java Virtual Machines"
  sudo cp -R /Volumes/OpenJDK\ 7\ \(Mac\ OS:X\ Port\)/1.7.0u.jdk /Library/Java/JavaVirtualMachines/1.7.0u6.jdk
  echo "Detach DMG image"
  hdiutil detach /Volumes/OpenJDK\ 7\ \(Mac\ OS:X\ Port\)
fi
