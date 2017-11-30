package top.jnaw.jee.platform.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseNotice<M extends BaseNotice<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setContent(java.lang.String content) {
		set("content", content);
	}

	public java.lang.String getContent() {
		return get("content");
	}

	public void setType(java.lang.Integer type) {
		set("type", type);
	}

	public java.lang.Integer getType() {
		return get("type");
	}

	public void setCreateId(java.lang.Integer createId) {
		set("create_id", createId);
	}

	public java.lang.Integer getCreateId() {
		return get("create_id");
	}

	public void setCreateTime(java.lang.String createTime) {
		set("create_time", createTime);
	}

	public java.lang.String getCreateTime() {
		return get("create_time");
	}

	public void setRepeal(java.lang.Integer repeal) {
		set("repeal", repeal);
	}

	public java.lang.Integer getRepeal() {
		return get("repeal");
	}

	public void setRemove(java.lang.Integer remove) {
		set("remove", remove);
	}

	public java.lang.Integer getRemove() {
		return get("remove");
	}

	public void setComment(java.lang.Integer comment) {
		set("comment", comment);
	}

	public java.lang.Integer getComment() {
		return get("comment");
	}

}