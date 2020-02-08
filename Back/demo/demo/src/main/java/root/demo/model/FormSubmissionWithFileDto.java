package root.demo.model;

import java.util.List;
import root.demo.model.FormSubmissonDTO;

public class FormSubmissionWithFileDto {

	private List<FormSubmissonDTO> form;
	private String file;
	private String fileName;
	
	public FormSubmissionWithFileDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FormSubmissionWithFileDto(List<FormSubmissonDTO> form, String file, String fileName) {
		super();
		this.form = form;
		this.file = file;
		this.fileName = fileName;
	}
	
	public List<FormSubmissonDTO> getForm() {
		return form;
	}
	public void setForm(List<FormSubmissonDTO> form) {
		this.form = form;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
