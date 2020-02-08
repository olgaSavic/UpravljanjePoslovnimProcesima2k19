export class FormSubmissionWithFileDto
{
  constructor(
    public form,
    public file : string,
    public fileName: string) {
  }
}
