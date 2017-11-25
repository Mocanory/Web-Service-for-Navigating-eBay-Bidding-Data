==================================================================This Folder is created for CS-144 Project 1.Team member:              Yue Xin    (UID: 204775695)  <yuexin@cs.ucla.edu>              Zhehan Li  (UID: 404888352)  <lizhehan@cs.ucla.edu>==================================================================************     Java NIO Memory-Mapped Files        *************In the ComputeSHA.java, we use the Java NIO Memory-Mapped Files to
significantly accelerate the file processing speed since this program
can be used to deal with large files.As we know, the system calls like read() would requires twice copies 
among disks, filesystem pages in the kernel space and memory area in
user space. While the Memory-Mapped I/O uses the filesystem to establish
a virtual memory mapping from user space directly to the applicable 
filesystem pages, which will reduce the times of coping to once.By using this technology, we reduce the processing time from 

[5666 ms] to [4787ms]

(Using the common read & write approach compared with memory-mapped 
files to compute the SHA of CS144.ova. Of course, the bigger the file
is, the more it will accelerate)==================================================================This Folder is created for CS-144 Project 1.Team member:              Yue Xin    (UID: 204775695)  <yuexin@cs.ucla.edu>              Zhehan Li  (UID: 404888352)  <lizhehan@cs.ucla.edu>==================================================================