# optimus-less [![Build Status](https://secure.travis-ci.org/magnars/optimus-less.png)](http://travis-ci.org/magnars/optimus-less)

A [LESS](http://lesscss.org/) asset loader for [Optimus](http://github.com/magnars/optimus).

## Install

Add `[optimus-less "0.1.0"]` to `:dependencies` in your `project.clj`.
It also requires Optimus version minimum 0.13.5.

## Usage

Add `(:require optimus-less.core)` to the namespace declaration where
you're loading assets.

The `load-less-asset` function will be declared as a custom loader for
`.less` files with Optimus' `load-asset` multimethod.

#### Considerations

- The `.less` files are immediately transpiled into `.css` files. This
  means you can safely bundle CSS and LESS files together. It also
  means that the bundle identifier has to be `.css`:

```cl
(assets/load-bundle "public"
                    "styles.css"
                    ["/styles/reset.less"
                     "/styles/main.less"])
```

- The less transpiler is fast. That doesn't mean you should be
  compiling the entirety of
  [Bootstrap.less](http://getbootstrap.com/). That takes about 350 ms,
  and will make your development environment sluggish. Use precompiled
  CSS sources for Bootstrap, and use optimus-less for your own files.

## Contribute

Yes, please do.

Maybe you'd like to add some caching so we could even include the raw
Bootstrap LESS files without a slowdown?

If you're looking to add other transpilers, I think that would be best
in a separate project. But please do let me know about it, so I can
link to it in the Optimus README. :-)

Remember add tests for your feature or fix, or I'll certainly break it
later.

#### Running the tests

`lein midje` will run all tests.

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.

## License

Copyright © 2013 Magnar Sveen

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
