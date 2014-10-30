class Settings < Settingslogic
  source "#{Rails.root}/config/omartech.yml"
  namespace Rails.env
end
