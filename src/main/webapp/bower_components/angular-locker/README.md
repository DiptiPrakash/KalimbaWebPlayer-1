<!--
This file has been generated using Gitdown (https://github.com/gajus/gitdown).
Direct edits to this will be be overwritten. Look for Gitdown markup file under ./.gitdown/ path.
-->
angular-locker
==============

A simple & configurable abstraction for local/session storage in angular projects - providing a fluent api that is powerful and easy to use.

[![Build Status](http://img.shields.io/travis/tymondesigns/angular-locker/master.svg?style=flat-square)](https://travis-ci.org/tymondesigns/angular-locker)
[![Code Climate](http://img.shields.io/codeclimate/github/tymondesigns/angular-locker.svg?style=flat-square)](https://codeclimate.com/github/tymondesigns/angular-locker)
[![Test Coverage](http://img.shields.io/codeclimate/coverage/github/tymondesigns/angular-locker.svg?style=flat-square)](https://codeclimate.com/github/tymondesigns/angular-locker)
[![License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat-square)](http://www.opensource.org/licenses/MIT)
[![NPM Release](https://img.shields.io/npm/v/angular-locker.svg?style=flat-square)](https://www.npmjs.org/package/angular-locker)
[![NPM Monthly Downloads](https://img.shields.io/npm/dm/angular-locker.svg?style=flat-square)](https://www.npmjs.org/package/angular-locker)

* [Installation](#installation)
* [Usage](#usage)
    * [Adding to your project](#usage-adding-to-your-project)
    * [Switching storage drivers](#usage-switching-storage-drivers)
    * [Switching namespace](#usage-switching-namespace)
    * [Adding items to locker](#usage-adding-items-to-locker)
    * [Retrieving items from locker](#usage-retrieving-items-from-locker)
    * [Checking item exists in locker](#usage-checking-item-exists-in-locker)
    * [Removing items from locker](#usage-removing-items-from-locker)
    * [Events](#usage-events)
    * [Binding to a $scope property](#usage-binding-to-a-scope-property)
* [Browser Compatibility](#browser-compatibility)
* [Contributing](#contributing)
* [Development](#development)
* [License](#license)


<h2 id="installation">Installation</h2>

<h4 id="installation-via-bower">via bower</h4>

```bash
$ bower install angular-locker
```

<h4 id="installation-via-npm">via npm</h4>

```bash
$ npm install angular-locker
```

<h4 id="installation-via-jsdelivr-cdn">via jsDelivr CDN</h4>

http://www.jsdelivr.com/#!angular.locker

<h4 id="installation-manual">manual</h4>

Simply download the zip file [HERE](https://github.com/tymondesigns/angular-locker/archive/master.zip) and include `dist/angular-locker.min.js` in your project.

1.73 kB Minified & gzipped.

<h2 id="usage">Usage</h2>

<h3 id="usage-adding-to-your-project">Adding to your project</h3>

Add `angular-locker` as a dependency

```js
angular.module('myApp', ['angular-locker'])
```

Configure via `lockerProvider` (*optional*)

```js
.config(['lockerProvider', function config(lockerProvider) {
    lockerProvider.setDefaultDriver('session')
                  .setDefaultNamespace('myAppName')
                  .setSeparator('.')
                  .setEventsEnabled(false);
}]);
```

*Note*: You can also pass `false` into `setDefaultNamespace()` if you prefer to not have a namespace in your keys.

inject `locker` into your controller/service/directive etc

```js
.factory('MyFactory', ['locker', function MyFactory(locker) {
    locker.put('someKey', 'someVal');
}]);
```

----------------------------

<h3 id="usage-switching-storage-drivers">Switching storage drivers</h3>

There may be times where you will want to dynamically switch between using local and session storage.
To achieve this, simply chain the `driver()` setter to specify what storage driver you want to use, as follows:

```js
// put an item into session storage
locker.driver('session').put('sessionKey', ['some', 'session', 'data']);

// this time use local storage
locker.driver('local').put('localKey', ['some', 'persistent', 'things']);
```

<h3 id="usage-switching-namespace">Switching namespace</h3>

```js
// add an item within a different namespace
locker.namespace('otherNamespace').put('foo', 'bar');
```

Omitting the driver or namespace setters will respect whatever default was specified via `lockerProvider`.

----------------------------

<h3 id="usage-adding-items-to-locker">Adding items to locker</h3>

there are several ways to add something to locker:

You can add Objects, Arrays, whatever :)

locker will automatically serialize your objects/arrays in local/session storage

```js
locker.put('someString', 'anyDataType');
locker.put('someObject', { foo: 'I will be serialized', bar: 'pretty cool eh' });
locker.put('someArray', ['foo', 'bar', 'baz']);
// etc
```

<h4 id="usage-adding-items-to-locker-adding-via-value-function-param">adding via value function param</h4>

Inserts specified key and return value of function

```js
locker.put('someKey', function() {
    var obj = { foo: 'bar', bar: 'baz' };
    // some other logic
    return obj;
});
```

The current value will be passed into the function so you can perform logic on the current value, before returning it. e.g.

```js
locker.put('someKey', ['foo', 'bar']);

locker.put('someKey', function(current) {
    current.push('baz');

    return current
});

locker.get('someKey') // = ['foo', 'bar', 'baz']
```

<h4 id="usage-adding-items-to-locker-adding-multiple-items-at-once-by-passing-a-single-object">adding multiple items at once by passing a single object</h4>

This will add each key/value pair as a **separate** item in storage

```js
locker.put({
    someKey: 'johndoe',
    anotherKey: ['some', 'random', 'array'],
    boolKey: true
});
```

<h4 id="usage-adding-items-to-locker-adding-via-key-function-param">adding via key function param</h4>

Inserts each item from the returned Object, similar to above

```js
locker.put(function() {
    // some logic
    return {
        foo: ['lorem', 'ipsum', 'dolor'],
        user: {
            username: 'johndoe',
            displayName: 'Johnny Doe',
            active: true,
            role: 'user'
        }
    };
});
```

<h4 id="usage-adding-items-to-locker-conditionally-adding-an-item-if-it-doesn-t-already-exist">conditionally adding an item if it doesn't already exist</h4>

For this functionality you can use the `add()` method.

If the key already exists then no action will be taken and `false` will be returned

```js
locker.add('someKey', 'someVal'); // true or false - whether the item was added or not
```

----------------------------

<h3 id="usage-retrieving-items-from-locker">Retrieving items from locker</h3>

```js
// locker.put('fooArray', ['bar', 'baz', 'bob']);

locker.get('fooArray'); // ['bar', 'baz', 'bob']
```

<h4 id="usage-retrieving-items-from-locker-setting-a-default-value">setting a default value</h4>

if the key does not exist then, if specified the default will be returned

```js
locker.get('keyDoesNotExist', 'a default value'); // 'a default value'
```

<h4 id="usage-retrieving-items-from-locker-retrieving-multiple-items-at-once">retrieving multiple items at once</h4>

You may pass an array to the `get()` method to return an Object containing the specified keys (if they exist)

```js
locker.get(['someKey', 'anotherKey', 'foo']);

// will return something like...
{
    someKey: 'someValue',
    anotherKey: true,
    foo: 'bar'
}
```

<h4 id="usage-retrieving-items-from-locker-deleting-afterwards">deleting afterwards</h4>

You can also retrieve an item and then delete it via the `pull()` method

```js
// locker.put('someKey', { foo: 'bar', baz: 'bob' });

locker.pull('someKey', 'defaultVal'); // { foo: 'bar', baz: 'bob' }

// then...

locker.get('someKey', 'defaultVal'); // 'defaultVal'
```

<h4 id="usage-retrieving-items-from-locker-all-items">all items</h4>

You can retrieve all items within the current namespace

This will return an object containing all the key/value pairs in storage

```js
locker.all();
// or
locker.namespace('somethingElse').all();
```

<h4 id="usage-retrieving-items-from-locker-counting-items">counting items</h4>

To count the number of items within a given namespace:

```js
locker.count();
// or
locker.namespace('somethingElse').count();
```

----------------------------

<h3 id="usage-checking-item-exists-in-locker">Checking item exists in locker</h3>

You can determine whether an item exists in the current namespace via

```js
locker.has('someKey') // true or false
// or
locker.namespace('foo').has('bar');

// e.g.
if (locker.has('user.authToken') ) {
    // we're logged in
} else {
    // go to login page or something
}
```

----------------------------

<h3 id="usage-removing-items-from-locker">Removing items from locker</h3>

The simplest way to remove an item is to pass the key to the `forget()` method

```js
locker.forget('keyToRemove');
// or
locker.driver('session').forget('sessionKey');
// etc..
```

<h4 id="usage-removing-items-from-locker-removing-multiple-items-at-once">removing multiple items at once</h4>

You can also pass an array.

```js
locker.forget(['keyToRemove', 'anotherKeyToRemove', 'something', 'else']);
```

<h4 id="usage-removing-items-from-locker-removing-all-within-namespace">removing all within namespace</h4>

you can remove all the items within the currently set namespace via the `clean()` method

```js
locker.clean();
// or
locker.namespace('someOtherNamespace').clean();
```
<h4 id="usage-removing-items-from-locker-removing-all-items-within-the-currently-set-storage-driver">removing all items within the currently set storage driver</h4>

```js
locker.empty();
```

----------------------------

<h3 id="usage-events">Events</h3>

There are 3 events that can be fired during various operations, these are:

```js
// fired when a new item is added to storage
$rootScope.$on('locker.item.added', function (e, payload) {
    // payload is equal to:
    {
        driver: 'local', // the driver that was set when the event was fired
        namespace: 'locker', // the namespace that was set when the event was fired
        key: 'foo', // the key that was added
        value: 'bar' // the value that was added
    }
});
```

```js
// fired when an item is removed from storage
$rootScope.$on('locker.item.forgotten', function (e, payload) {
    // payload is equal to:
    {
        driver: 'local', // the driver that was set when the event was fired
        namespace: 'locker', // the namespace that was set when the event was fired
        key: 'foo', // the key that was removed
    }
});
```

```js
// fired when an item's value changes to something new
$rootScope.$on('locker.item.updated', function (e, payload) {
    // payload is equal to:
    {
        driver: 'local', // the driver that was set when the event was fired
        namespace: 'locker', // the namespace that was set when the event was fired
        key: 'foo', // the key that was updated
        oldValue: 'bar', // the value that was set before the item was updated
        newValue: 'baz' // the new value that the item was updated to
    }
});
```

----------------------------

<h3 id="usage-binding-to-a-scope-property">Binding to a $scope property</h3>

You can bind a scope property to a key in storage. Whenever the $scope value changes, it will automatically be persisted in storage. e.g.

```js
app.controller('AppCtrl', ['$scope', function ($scope) {

    locker.bind($scope, 'foo');

    $scope.foo = ['bar', 'baz'];

    locker.get('foo') // = ['bar', 'baz']

}]);
```

You can also set a default value via the third parameter:

```js
app.controller('AppCtrl', ['$scope', function ($scope) {

    locker.bind($scope, 'foo', 'someDefault');

    $scope.foo // = 'someDefault'

    locker.get('foo') // = 'someDefault'

}]);
```

To unbind the $scope property, simply use the unbind method:


```js
app.controller('AppCtrl', ['$scope', function ($scope) {

    locker.unbind($scope, 'foo');

    $scope.foo // = undefined

    locker.get('foo') // = undefined

}]);
```

----------------------------


<h2 id="browser-compatibility">Browser Compatibility</h2>

IE8 is not supported because I am utilising `Object.keys()`

To check if the browser natively supports local and session storage, you can do the following:

```js
if (! locker.supported()) {
    // load a polyfill?
}
```

I would recommend using [Remy's Storage polyfill](https://gist.github.com/remy/350433) if you want to support older browsers.

For the latest browser compatibility chart see [HERE](http://caniuse.com/namevalue-storage)

<h2 id="contributing">Contributing</h2>

In lieu of a formal styleguide, take care to maintain the existing coding style. Add unit tests for any new or changed functionality. Lint and test your code using Gulp.

<h2 id="development">Development</h2>

```bash
$ npm install
$ bower install
$ gulp
```

<h2 id="license">License</h2>

The MIT License (MIT)

Copyright (c) 2014 Sean Tymon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
