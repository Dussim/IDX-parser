# IDX-parser
Program for parsing hand-written numbers in IDX format

Parser for files from http://yann.lecun.com/exdb/mnist/
 
 ```
  LABELS format
  [offset] [type]          [value]          [description]
  0000     32 bit integer  0x00000801(2049) magic number (MSB first)
  0004     32 bit integer  60000            number of items
  0008     unsigned byte   ??               label
  0009     unsigned byte   ??               label
  ........
  xxxx     unsigned byte   ??               label
 
 
  NUMBERS format
  [offset] [type]          [value]          [description]
  0000     32 bit integer  0x00000803(2051) magic number
  0004     32 bit integer  60000            number of images
  0008     32 bit integer  28               number of rows
  0012     32 bit integer  28               number of columns
  0016     unsigned byte   ??               pixel
  0017     unsigned byte   ??               pixel
  ........
  xxxx     unsigned byte   ??               pixel
  ```
  
  Example of using
  
  ```kotlin
  val NUMBERS_FILE_PATH = "path/to/my/number/file"
  val LABELS_FILE_PATH = "path/to/my/labels/file"
  
  val idx = IDX(NUMBERS_FILE_PATH, LABELS_FILE_PATH)
  
  idx.use { numbers ->
    numbers.forEach { number ->
      //do something with number
    }
  }
  ```
  
  IDX class implements `Iterator` and `Closeable` interfaces. It will be better to use it with `use` kotlin closure. 
  `IDX.next()` returns `Pair<Int, Array<Int>>` where `first` is value of hand-written number in range 0..9 and `second` is
  list of pixels with values in range 0..255
