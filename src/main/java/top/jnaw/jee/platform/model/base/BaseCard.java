package top.jnaw.jee.platform.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseCard<M extends BaseCard<M>> extends Model<M> implements IBean {

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

	public void setContext(java.lang.String context) {
		set("context", context);
	}

	public java.lang.String getContext() {
		return get("context");
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

	public void setLooker(java.lang.String looker) {
		set("looker", looker);
	}

	public java.lang.String getLooker() {
		return get("looker");
	}

	public void setTypeId(java.lang.Integer typeId) {
		set("type_id", typeId);
	}

	public java.lang.Integer getTypeId() {
		return get("type_id");
	}

	public void setImportant(java.lang.String important) {
		set("important", important);
	}

	public java.lang.String getImportant() {
		return get("important");
	}

	public void setSpare(java.lang.String spare) {
		set("spare", spare);
	}

	public java.lang.String getSpare() {
		return get("spare");
	}

	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	public java.lang.Integer getStatus() {
		return get("status");
	}

	public void setRemove(java.lang.Integer remove) {
		set("remove", remove);
	}

	public java.lang.Integer getRemove() {
		return get("remove");
	}

}
