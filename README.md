# DSA-Asignment-2

## Write a Vietnamese-English dictionary program that has the following functions:
  1.	Translate Vietnamese word into English word.
  2.	Translate English word into Vietnamese word.
  3.	Add a Vietnamese-English vocalurary
  4.	Add a English- Vietnamese vocalurary
  5.	Save set of Vietnamese-English vocaluraries and Vietnamese-English vocaluraries to files.
  
## Requirements:

(1)	The program is GUI application
(2)	Set of Vietnamese-English vocaluraries will be stored in a text file. Assume format of the file:
  -	There is only one entry per line.
  -	c. A Vietnamese word is separated by a colon from its English equivalent(s); if there is more than one equivalent, they are       separated by a comma.
  
      For example:
  
      ai : who
  
      biết : know
  
      cổ xưa : ancient
  
      cùng : and
  
      động vật : animal
  
      đốt cháy : burn, be on fire, desire
  
      trước : before, in front of, previously
  
      và : and
  
      vàng : gold
  
      vũ khí : arms, weapons
  
  -	Vietnamese words are stored in alphabet order
  
(3)	Set of English- Vietnamese vocaluraries are stored one separate file which has the same format with the Vietnamese-English file.

(4)	You have to use two binary search trees. One for containing Vietnamese words and English equivalents (Viet-Anh), one for containing English words and Vietnamese equivalents (Anh-Viet).

(5)	In binary search tree of Viet-Anh, each node contains one Vietnamese word and one linked list of English equivalents. Make sure that there is only one node for each Vietnamese word in the tree. Do the same to binary search tree of Anh-Viet.

(6)	These two tree will be create when the program is initialized. 

(7)	A Vietnamese-English or English- Vietnamese vocalurary will be add to both tree.

(8)	If Save function is used, each binary search tree will stored in corresponding file. Note: Words are stored in alphabet order.
