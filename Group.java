/*package Vomber;

import java.util.*;

public class Group {
	VomberDAO dao = new VomberDAO();
	int group_num = 0;
	int num=0; // vocabulary�� ���� ��
	int gr=0; // �� �׷��� ��
	String id;
	Hashtable<String, ArrayList<VomberDTO>> hash;
	ArrayList<VomberDTO> list;
	Iterator<VomberDTO> iter;
	VomberDTO dto[] = new VomberDTO[num];
	String group[] = new String[num]; //vocabulary�� ��ϵ� 
	String group_list[] = new String[gr];
	
	public Group() {
		id=dao.getID();
		hash =dao.hashVo(id);
		list = hash.get(id);
		iter = list.iterator();
		while(iter.hasNext()) {
			dto[num] = iter.next();
			group[num] = dto[num].getVogroup();
			for(int i =0; i<num; ++i) {
				if(group[i].trim().equals(group[num].trim())) {
					group[num] = null; // group�� null�̸� tab�� ����������
				}
			}++num;
		}
		for(int i=0; i<num; ++i) {
			if(group[num]!=null) {
				++group_num;
				
			}
		}
	}
}
*/

