package cn.util;

import java.util.List;

public class TreeNode {

	private String id;
	private String treeName;
	private String parentId;
	private String sortNo;
	private String icon;
	private String url;
	private List<TreeNode> chList;
	
	public TreeNode() {
		super();
	}

	public TreeNode(String treeName) {
		super();
		this.treeName = treeName;
	}
	
	public TreeNode(String id, String treeName, String parentId, String sortNo,
			List<TreeNode> chList) {
		super();
		this.id = id;
		this.treeName = treeName;
		this.parentId = parentId;
		this.sortNo = sortNo;
		this.chList = chList;
	}

	public List<TreeNode> getChList() {
		return chList;
	}
	public void setChList(List<TreeNode> chList) {
		this.chList = chList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTreeName() {
		return treeName;
	}
	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}


	@Override
	public String toString() {
		return "名称=" + treeName + ", 子节点=" + chList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	

	
	
}
