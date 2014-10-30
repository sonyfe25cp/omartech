class AttachmentUploader < CarrierWave::Uploader::Base
  storage :file

  def store_dir
    "uploads/#{model.class.to_s.underscore}/#{mounted_as}/#{model.id}"
  end

  def extension_white_list
    Settings.send(task_type).accepted_files
  end

  private
  def task_type
    model.task_type.underscore
  end
end
