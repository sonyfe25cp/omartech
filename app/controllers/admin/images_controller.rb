require 'carrierwave/datamapper'

class Admin::ImageUploader < CarrierWave::Uploader::Base
    include CarrierWave::MiniMagick
end
