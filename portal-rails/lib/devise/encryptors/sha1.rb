require 'digest/sha1'

module Devise
  module Encryptable
    module Encryptors
      class Sha1 < Base
        def self.digest(password, stretches, salt, pepper)
          str = [password, salt].flatten.compact.join
          Digest::SHA1.hexdigest(str)
        end
      end
    end
  end
end
