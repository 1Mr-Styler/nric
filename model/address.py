# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = (r'''(?P<addr>(?:no\.?|lorong|jalan)+\s\d+[a-zA-Z0-9/\s]+\d{5}\s[a-zA-Z]{4,}\n[a-zA-Z]+)''')

test_str = ('''--text--''')

matches = re.finditer(regex, test_str, re.MULTILINE)
data = ""
for matchNum, match in enumerate(matches):
    matchNum = matchNum + 1
    print("{0}".format(match.group('addr')))


# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.
