/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: �˵��ڵ���ʾ����/p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author dingzhong
 * @version 1.0
 */
public class MenuShow
{

//    String ls_location = "���ڵ�λ�ã�";
    StringBuffer ls_location = new StringBuffer("���ڵ�λ�ã�");


    public MenuShow()
    {
    }

    /**
     * ������и��ڵ�
     * @param strNode String[][]
     * 0 parent; 1 nodecode 2 nodename 3 childflag 4 runscript
     * @param NodeCount int
     * @return String
     */
    public String getAllLeafNodePath(String[][] strNode, int NodeCount)
    {
        StringBuffer resultStr = new StringBuffer(256);
        int count = 0; // count the leafnode num of strNode
        for (int i = 0; i < NodeCount; i++)
        {
            if (strNode[i][3].compareTo("0") != 0) //it is not a leaf node
            {
                continue;
            }

            // now make the leafnode's string ,it's format is
            // "fullpathname-leafnodename-runscript"

            //make leaf node's full path name
            if (count != 0)
            {
//                resultStr.append(resultStr);
                resultStr.append("^");
//                resultStr += "^";
            }

//            String nameStr = strNode[i][2];
            StringBuffer nameStr = new StringBuffer("256");
            int index = i - 1;
            String parentCode = strNode[i][0];
            while (index >= 0 && parentCode.compareTo("0") != 0)
            {
                if (strNode[index][1].compareTo(parentCode) == 0)
                {
//                    nameStr = strNode[index][2] + "-" + nameStr;
                    nameStr.append(strNode[index][2]);
                    nameStr.append("-");
                    nameStr.append(strNode[i][2]);
                    parentCode = strNode[index][0];
                }
                index--;
            }
            //make up the leafnode string
//            resultStr.append(resultStr);
            resultStr.append(nameStr);
            resultStr.append("|");
            resultStr.append(strNode[i][1]);
            resultStr.append("|");
            resultStr.append(strNode[i][4]);
//            resultStr += nameStr + "|" + strNode[i][1] + "|" + strNode[i][4];
            count++;

        }
        return resultStr.toString();
    }

    /**
     * ���ѭ��
     * @param strNode String[][]
     * @param intIndex int
     * @param NodeCount int
     * @param menuCount int[]
     * @return String
     */
    public String getMenu(String[][] strNode, int intIndex, int NodeCount,
                          int[] menuCount)
    {
//        String strMenuReturn = "";
//        String strMenu = "";
        StringBuffer strMenuReturn = new StringBuffer(256);
        StringBuffer strMenu = null;
        StringBuffer menuStr = null;

        int z = 1;
        for (int j = intIndex; j < NodeCount; j++)
        {

            if (strNode[j][0].compareTo("0") == 0)
            {
                int[] childNum = new int[1];
                childNum[0] = 0;
//                String temp = "";
                //���³�ʼ��StringBuffer
                strMenu = new StringBuffer(256);
                strMenu.append("Menu");
                strMenu.append(z);
//                strMenu = "Menu" + z;

                String childStr = "";
//                String menuStr = "";
//                StringBuffer menuStr = new StringBuffer(256);

                //�õ��ӽڵ�˵��ַ���
                if (strNode[j][3].compareTo("0") != 0)
                {
                    // �ж��Ƿ�����ĵ����������ӽڵ�
                    if (j == NodeCount - 1)
                    {
                        break;
                    }
                    if (strNode[j + 1][0].compareTo(strNode[j][1]) == 0)
                    {
//                        strMenu.append(strMenu);
                        strMenu.append("_");
//                        strMenu = strMenu + "_";
                        //���������Ӳ˵�����
                        childStr = getMenuChild(strNode, j + 1, strNode[j][3],
                                                strMenu.toString(),
                                                NodeCount, childNum);
                        if (childNum[0] == 0)
                        {
                            continue;
                        }
                    }
                }

                if (strNode[j][3].compareTo("0") != 0 && childNum[0] == 0)
                {
                    continue;
                }

                if ((strNode[j][4].compareTo("null") == 0) ||
                    (strNode[j][4].compareTo("") == 0) || (strNode[j][4] == null))
                {
                    menuStr = new StringBuffer(256);
                    menuStr.append("Menu");
                    menuStr.append(z);
                    menuStr.append("=new Array('");
                    menuStr.append(strNode[j][2]);
                    menuStr.append("','','',");
                    menuStr.append(childNum[0]);
                    menuStr.append(",MenuHeight,120,'','','','','','',-1,1,-1,'','");
                    menuStr.append(strNode[j][1]);
                    menuStr.append("');");
//                    menuStr = "Menu" + z + "=new Array('" + strNode[j][2] + "','',''," +
//                              childNum[0] +
//                              ",MenuHeight,120,'','','','','','',-1,1,-1,'','" +
//                              strNode[j][1] + "');";
                }
                else
                {
                    menuStr = new StringBuffer(256);
                    menuStr.append("Menu");
                    menuStr.append(z);
                    menuStr.append("=new Array('");
                    menuStr.append(strNode[j][2]);
                    menuStr.append("','");
                    menuStr.append(strNode[j][4]);
                    menuStr.append("','',");
                    menuStr.append(childNum[0]);
                    menuStr.append(",MenuHeight,120,'','','','','','',-1,1,-1,'','");
                    menuStr.append(strNode[j][1]);
                    menuStr.append("');");
//                    menuStr = "Menu" + z + "=new Array('" + strNode[j][2] +
//                              "','" + strNode[j][4] + "',''," + childNum[0] +
//                              ",MenuHeight,120,'','','','','','',-1,1,-1,'','" +
//                              strNode[j][1] + "');";
                }

                z++;
                menuCount[0]++;
//                strMenuReturn.append(strMenuReturn);
                strMenuReturn.append(menuStr);
                strMenuReturn.append(childStr);
//                strMenuReturn += menuStr.toString() + childStr;
            }
        }
        return strMenuReturn.toString();
    }

    /**
     * �Ӳ˵��ݹ����ɺ���
     * @param strNode String[][] �˵��ڵ��ά����
     * @param intIndex int ��һ���ӽڵ���ܵ�λ��
     * @param ChildCount String �˵��ڵ�ӵ�е��ӽڵ�����
     * @param strMenu String �ӽڵ�˵�ǰ׺
     * @param nodeCount int ȫ�ֲ˵��ڵ�����
     * @param childNum int[]
     * @return String
     */
    public String getMenuChild(String[][] strNode, int intIndex,
                               String ChildCount, String strMenu, int nodeCount,
                               int[] childNum)
    {
        //�˵��ڵ�ӵ�е�ʵ���ӽڵ���
        childNum[0] = 0;

//        String strMenuChildReturn = "";
        StringBuffer strMenuChildReturn = new StringBuffer(256);
        StringBuffer strMenus = null;

        int k = intIndex;
        String strParent = strNode[intIndex][0]; //ȡ�õ�ǰ�ڵ�ĸ��ڵ�

        Integer tInteger = new Integer(ChildCount); //ת�����ڵ���ֽڵ�����
        int ForCount = tInteger.intValue();

        for (int y = 1; y <= ForCount; y++)
        {
            if (intIndex >= nodeCount)
            {
                break;
            }

            //�ж��Ƿ�ǰ�ڵ�Ϊ���ڵ㣬�������ݹ���ú���getMenuChild
            if (strNode[intIndex][3].compareTo("0") != 0)
            {

                int[] myChildNum = new int[1];

                if (strNode[intIndex + 1][0].compareTo(strNode[intIndex][1]) ==
                    0)
                {
                    int tempid = childNum[0] + 1;

//                    String strMenus = strMenu + tempid + "_"; //�ݹ��Σ�0�㣺menu1��1��:menu1_1��2�㣺menu1_1_1������
                    strMenus = new StringBuffer(256);
                    strMenus.append(strMenu);
                    strMenus.append(tempid);
                    strMenus.append("_");

                    String temp = getMenuChild(strNode, intIndex + 1, strNode[intIndex][3],
                                               strMenus.toString(), nodeCount, myChildNum);

                    //myChildNum��Ϊ0��ʾ���˵��ڵ�����Ч��
                    if (myChildNum[0] != 0)
                    {
                        childNum[0]++;

                        strMenuChildReturn.append(strMenu);
                        strMenuChildReturn.append(childNum[0]);
                        strMenuChildReturn.append("=new Array('");
                        strMenuChildReturn.append(strNode[intIndex][2]);
                        strMenuChildReturn.append("','','',");
                        strMenuChildReturn.append(myChildNum[0]);
                        strMenuChildReturn.append(",MenuHeight,120,'','','','','','',-1,1,-1,'','");
                        strMenuChildReturn.append(strNode[intIndex][1]);
                        strMenuChildReturn.append("');");
//                        strMenuChildReturn += strMenu + childNum[0] +
//                                "=new Array('" +
//                                strNode[intIndex][2] + "','',''," +
//                                myChildNum[0] +
//                                ",MenuHeight,120,'','','','','','',-1,1,-1,'','" +
//                                strNode[intIndex][1] + "');";

                        strMenuChildReturn.append(temp);
//                        strMenuChildReturn = strMenuChildReturn + temp;
                    }

                    if (intIndex == nodeCount)
                    {
                        break;
                    }
                }
            }
            else
            {
                childNum[0]++;

                strMenuChildReturn.append(strMenu);
                strMenuChildReturn.append(childNum[0]);
//                strMenuChildReturn = strMenuChildReturn + strMenu + childNum[0];
                //�ж��Ƿ�����Ӧ���ӣ�����Ӧ�ı���ɫ
                //���ڸ������ѡȡ�����¿ձ�������null�ַ���
                if ((strNode[intIndex][4].compareTo("null") == 0) ||
                    (strNode[intIndex][4].compareTo("") == 0) ||
                    (strNode[intIndex][4] == null))
                {
                    strMenuChildReturn.append("=new Array('");
                    strMenuChildReturn.append(strNode[intIndex][2]);
                    strMenuChildReturn.append("','','',");
                    strMenuChildReturn.append(strNode[intIndex][3]);
                    strMenuChildReturn.append(",MenuHeight,120,'','','','','','',-1,1,-1,'','");
                    strMenuChildReturn.append(strNode[intIndex][1]);
                    strMenuChildReturn.append("');");

//                    strMenuChildReturn = strMenuChildReturn + "=new Array('" +
//                                         strNode[intIndex][2] + "','',''," +
//                                         strNode[intIndex][3] +
//                                         ",MenuHeight,120,'','','','','','',-1,1,-1,'','" +
//                                         strNode[intIndex][1] + "');";
                }
                else
                {
                    strMenuChildReturn.append("=new Array('");
                    strMenuChildReturn.append(strNode[intIndex][2]);
                    strMenuChildReturn.append("','");
                    strMenuChildReturn.append(strNode[intIndex][4]);
                    strMenuChildReturn.append("','',");
                    strMenuChildReturn.append(strNode[intIndex][3]);
                    strMenuChildReturn.append(",MenuHeight,120,'','','','','','',-1,1,-1,'','");
                    strMenuChildReturn.append(strNode[intIndex][1]);
                    strMenuChildReturn.append("');");

//                    strMenuChildReturn = strMenuChildReturn + "=new Array('" +
//                                         strNode[intIndex][2] + "','" +
//                                         strNode[intIndex][4] + "',''," +
//                                         strNode[intIndex][3] +
//                                         ",MenuHeight,120,'','','','','','',-1,1,-1,'','" +
//                                         strNode[intIndex][1] + "');";
                }

            }
            //��ָ��ָ�����ڵ�ΪstrParent����һ������

            k = nodeCount;
            if (intIndex + 1 < nodeCount)
            {
                for (int i = intIndex + 1; i < nodeCount; i++)
                {
                    if (strNode[i][0].compareTo(strParent) == 0)
                    {
                        k = i; //һ���ҵ��������˳�ѭ��
                        break;
                    }
                }
            }

            intIndex = k; //������ָ�����ݸ���intIndex

        }
        return strMenuChildReturn.toString();
    }

    /**
     * ��ýڵ�
     * @param menuID int ����ڵ���е���ʾ�˵��ı��
     * @param strNode String[][] �˵��ڵ��ά����
     * @param arrayOffset int
     * @return String
     */
    public String advanced_getMenu(int menuID, String[][] strNode,
                                   int arrayOffset)
    {
        // �˽ڵ���strNode�е�λ��
        String result = "";
        return result;
    }

    /**
     * ��ȡ�˵���չ·��
     * @param nodecode String
     * @return String
     */
    public String getStation(String nodecode)
    {
//        LDMenuDB tLDMenuDB = new LDMenuDB();
//        LDMenuSet tLDMenuSet = new LDMenuSet();
//        LDMenuSchema tLDMenuSchema = new LDMenuSchema();
//        tLDMenuDB.setNodeCode(nodecode);
//        String tsql = "";
//        tsql = "select * from LDMenu where nodecode = '" +
//               nodecode + "'";
//        tLDMenuSet = tLDMenuDB.executeQuery(tsql);
//        int i, iMax;
//        iMax = tLDMenuSet.size();
//        for (i = 1; i <= iMax; i++)
//        {
//            tLDMenuSchema = tLDMenuSet.get(i);
//        }
//        if (tLDMenuSchema.getParentNodeCode().compareTo("0") != 0)
//        {
//            getStation(tLDMenuSchema.getParentNodeCode());
//        }

        SSRS tSSRS = new SSRS();
        ExeSQL tExeSQL = new ExeSQL();
        StringBuffer tSql = new StringBuffer();

        tSql.append("select NodeCode,NodeName,ParentNodeCode from LDMenu where NodeCode = '");
        tSql.append(nodecode);
        tSql.append("'");

        tSSRS = tExeSQL.execSQL(tSql.toString());

        if (tSSRS.GetText(1, 3).compareTo("0") != 0)
        {
            getStation(tSSRS.GetText(1,3));
        }

        ls_location.append(tSSRS.GetText(1, 2));
        ls_location.append("-->");
//        ls_location = ls_location + tSSRS.GetText(1, 2) + "-->";
        return ls_location.toString();
    }

    public static void main(String[] args)
    {
//        MenuShow a = new MenuShow();
//        a.getStation("1003");
//        int[] menuCount = new int[1];
//        menuCount[0] = 0;
//        VData tData = new VData();
//        LDUserSchema tLDUserSchema = new LDUserSchema();
//        LDMenuQueryUI tLDMenuQueryUI = new LDMenuQueryUI();
//        String userCode = "O3";
//        tLDUserSchema.setUserCode(userCode);
//        tData.add(tLDUserSchema);
//        tLDMenuQueryUI.submitData(tData, "query");
//        tData = tLDMenuQueryUI.getResult();
//        int node_count = tLDMenuQueryUI.getResultNum();
//        String[][] node = new String[node_count][5];
//        String tStr = "";
//        tStr = tLDMenuQueryUI.getResultStr();
//        String sl = tStr;
//        sl += SysConst.RECORDSPLITER;
//        //����������ȷ���ַ������������¸�ֵ
//        int i = 0;
//        for (i = 0; i < node_count; i++)
//        {
//            String str = StrTool.decodeStr(sl, SysConst.RECORDSPLITER, i + 1);
//            for (int j = 0; j < 5; j++)
//            {
//                str += "|";
//                node[i][0] = StrTool.decodeStr(str, SysConst.PACKAGESPILTER, 1);
//                node[i][1] = StrTool.decodeStr(str, SysConst.PACKAGESPILTER, 2);
//                node[i][2] = StrTool.decodeStr(str, SysConst.PACKAGESPILTER, 3);
//                node[i][3] = StrTool.decodeStr(str, SysConst.PACKAGESPILTER, 4);
//                node[i][4] = StrTool.decodeStr(str, SysConst.PACKAGESPILTER, 5);
//            }
//        }
//        //�������ɲ˵�����
//        System.out.println("menuCount before getMenu");
//        System.out.println(menuCount[0]);
//        MenuShow tmenuShow = new MenuShow();
//        System.out.println("nodecount:" + node_count);
//        System.out.println("menuCount" + menuCount);
//        String menushowstr = tmenuShow.getMenu(node, 0, node_count, menuCount);
//        System.out.println("menuCount after getMenu");
//        System.out.println(menuCount[0]);
//        int totalMenu = menuCount[0] + 3;
//        System.out.println("complete getMenu");
    }
}
