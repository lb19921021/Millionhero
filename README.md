根据https://github.com/lingfengsan/MillionHero 修改
将tessOCR修改为使用汉王识别。一般情况下可以4秒打开搜索的网页


lingfengsan的原代码 可以百度搜索并统计搜索得到结果数量  但是换用汉王ocr后识别有部分问题
所以删减了这部分  

原理说明：
通过adb截图传送到电脑  然后裁剪图片保留题目部分   通过ocr识别文字  打开chrome百度页面