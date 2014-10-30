source 'http://ruby.taobao.org'

gem 'rails', '3.2.9'

# Bundle edge Rails instead:
# gem 'rails', :git => 'git://github.com/rails/rails.git'



# Gems used only for assets and not required
# in production environments by default.
group :assets do
  gem 'sass-rails',   '~> 3.2.3'
  gem 'coffee-rails', '~> 3.2.1'

  # See https://github.com/sstephenson/execjs#readme for more supported runtimes
  gem 'therubyracer', :platforms => :ruby

  gem 'uglifier', '>= 1.0.3'
end

gem 'jquery-rails'

# To use ActiveModel has_secure_password
# gem 'bcrypt-ruby', '~> 3.0.0'

# To use Jbuilder templates for JSON
# gem 'jbuilder'

# Use unicorn as the app server
# gem 'unicorn'

# Deploy with Capistrano
# gem 'capistrano'

# To use debugger
# gem 'debugger'

DM_VERSION = '~> 1.2.0'

gem 'dm-rails',         DM_VERSION
gem 'dm-migrations',    DM_VERSION
gem 'dm-types',         DM_VERSION
gem 'dm-validations',   DM_VERSION
gem 'dm-constraints',   DM_VERSION
gem 'dm-transactions',  DM_VERSION
gem 'dm-aggregates',    DM_VERSION
gem 'dm-timestamps',    DM_VERSION
gem 'dm-observer',      DM_VERSION
gem 'dm-mysql-adapter', DM_VERSION
gem 'dm-serializer',    DM_VERSION
gem 'dm-tags', :git => 'https://github.com/towerhe/dm-tags.git', :branch => 'support-unicode'
gem 'dm-searcher'
gem 'dm-devise'

#weibo_oauth
gem 'omniauth'
gem 'omniauth-weibo-oauth2'
#gem 'weibo_2'

#renren_oauth
#gem 'omniauth-renren'

gem 'carrierwave-datamapper',     :require => 'carrierwave/datamapper' #上传插件
gem 'seedbank'
gem 'twitter-bootstrap-rails', :git => 'git://github.com/seyhunak/twitter-bootstrap-rails.git'
gem 'haml-rails'
gem 'simple_form'
gem 'kaminari'
gem 'cells'
gem 'jquery-fileupload-rails'
gem 'exception_notification'
gem 'gravtastic'
gem 'oj'
gem 'settingslogic'
gem 'devise-encryptable'
gem 'newrelic_rpm'
gem 'less-rails'

group :development, :test do
  gem 'rspec-rails'
  gem 'yard'
  gem 'pry'
  gem 'quiet_assets'
  gem 'thin'
end

group :test do
  gem 'factory_girl_rails', '~> 4.0'
  gem 'fivemat'
  gem 'ffaker'
  gem 'dm-rspec2'
  gem 'json_spec'
  gem 'capybara'
  #gem 'capybara-webkit'
  gem 'database_cleaner'
end
