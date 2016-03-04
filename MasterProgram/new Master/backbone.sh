#!/bin/bash

scp AddingUniqueValues.java DuplicateMainProgram.java FilesMerger.java MainProgram.java MergeManager.java MergeSort.java MergeSortController.java ReceiveFrommaster.java SendSortedFile.java SlavePing.java WriteToFile.java restart_pi_121.sh pi@10.10.10.121:/home/pi/SlaveCode/check_ssh

ssh pi@10.10.10.121 '
cd /home/pi/group2
chmod 700 restart_pi_121.sh
javac *.java
java MainProgram
'

scp AddingUniqueValues.java DuplicateMainProgram.java FilesMerger.java MainProgram.java MergeManager.java MergeSort.java MergeSortController.java ReceiveFrommaster.java SendSortedFile.java SlavePing.java WriteToFile.java restart_pi_122.sh pi@10.10.10.122:/home/pi/SlaveCode/check_ssh

ssh pi@10.10.10.122 '
/home/pi/group2
chmod 700 restart_pi_122.sh
javac *.java
java MainProgram
'

scp AddingUniqueValues.java DuplicateMainProgram.java FilesMerger.java MainProgram.java MergeManager.java MergeSort.java MergeSortController.java ReceiveFrommaster.java SendSortedFile.java SlavePing.java WriteToFile.java restart_pi_133.sh pi@10.10.10.133:/home/pi/SlaveCode/check_ssh

ssh pi@10.10.10.133 '
/home/pi/group2
chmod 700 restart_pi_133.sh
javac *.java
java MainProgram
'

scp AddingUniqueValues.java DuplicateMainProgram.java FilesMerger.java MainProgram.java MergeManager.java MergeSort.java MergeSortController.java ReceiveFrommaster.java SendSortedFile.java SlavePing.java WriteToFile.java restart_pi_134.sh pi@10.10.10.134:/home/pi/SlaveCode/check_ssh

ssh pi@10.10.10.134 '
/home/pi/group2
chmod 700 restart_pi_134.sh
javac *.java
java MainProgram
'

scp AddingUniqueValues.java DuplicateMainProgram.java FilesMerger.java MainProgram.java MergeManager.java MergeSort.java MergeSortController.java ReceiveFrommaster.java SendSortedFile.java SlavePing.java WriteToFile.java restart_pi_136.sh pi@10.10.10.136:/home/pi/SlaveCode/check_ssh

ssh pi@10.10.10.136 '
/home/pi/group2
chmod 700 restart_pi_136.sh
javac *.java
java MainProgram
'

scp AddingUniqueValues.java DuplicateMainProgram.java FilesMerger.java MainProgram.java MergeManager.java MergeSort.java MergeSortController.java ReceiveFrommaster.java SendSortedFile.java SlavePing.java WriteToFile.java restart_pi_137.sh pi@10.10.10.137:/home/pi/SlaveCode/check_ssh

ssh pi@10.10.10.137 '
/home/pi/group2
chmod 700 restart_pi_137.sh
javac *.java
java MainProgram
'

