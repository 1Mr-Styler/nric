# coding=utf8
# the above tag defines encoding for this document and is for Python 2.x compatibility

import re

regex = (r'''Surname.*\n(?P<sname>[A-Z]+)\n.*\n(?P<fname>[A-Z]+)\n.*\n(?P<nationality>[A-Z]+)\n.*\n(?P<dob>[0-9A-Z\s]+)\n.*\n(?P<sex>[A-Z]+)\s(?P<pob>[A-Z\s]+)\n.*\n(?P<issue>[0-9A-Z\s\?]+)\n.*\n(?P<expire>[0-9A-Z\s\?]+)\n''')

test_str = ('''--text--''')

matches = re.finditer(regex, test_str, re.MULTILINE)
data = ""
for matchNum, match in enumerate(matches):
	print match.groupdict()


# Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.
