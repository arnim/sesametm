# RTM-SesameTM Integration #
[Ruby Topic Maps (RTM)](http://rubygems.org/gems/rtm) provides [Ruby](http://www.ruby-lang.org)-style language bindings upon wrapping TMAPI conforme engines.

## Installation ##
[RTM-SesameTM](http://rubygems.org/gems/rtm-sesametm) is available through [RubyGems](http://rubygems.org/), the official Ruby package management system. Assuming [JRuby](http://jruby.org/) installed and configured it can be installed by
```
sudo jgem install rtm-sesametm
```
or
```
sudo jruby -S gem install rtm-sesametm
```
Both commands install RTM-SesameTM and all its dependencies. RTM-SesameTM already includes SesameTM, so there is no need to install it manually.

## Loading ##
To be used in an application, RTM-SesameTM has to be loaded. As usual in Ruby this is done using the require statement.
```
require 'rubygems'
require 'rtm/sesametm'
```

## Usage ##
Once installed and loaded RTM-SesameTM behaves like any other backend for RTM; thus
```
base = RTM.connect :backend => :sesametm
tm = base.create "base:iri"
topic = tm.get! "first:topic"
```
creates a new Topic Map tm with the specified IRI "base:iri"
as well as a Topic.
For further reference on the usage of Ruby Topic Maps see [RTM documentation](http://docs.topicmapslab.de/rtm/)