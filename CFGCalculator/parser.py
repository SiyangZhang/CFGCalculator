
class Parser:

	def __init__(self, input, cursor):
		self.input = input
		self.cursor = cursor

	def setInput(self, input):
		self.input = input + "e"

	def parseExpression(self):
		term = self.parseTerm()
		tail = self.parseExpressionTail()
		return term + tail
	
	def parseExpressionTail(self):
		if(not isAddOp(self.peek())):
			return 0

		op = self.parseAddOp()
		term = self.parseTerm()
		if(op == "-"):
			term = 0 - term
		tail = self.parseExpressionTail()

		return term + tail
	
	def parseTerm(self):
		head = self.parseFactor()
		tail = self.parseTermTail()
		return head * tail
	
	def parseTermTail(self):
		if(not isMultiOp(self.peek())):
			return 1

		op = self.parseMultiOp()
		factor = self.parseFactor()
		if(op == "/"):
			factor = 1.0 / factor
		tail = self.parseTermTail()

		return factor * tail

	def parseFactor(self):
		value = None
		if(self.peek() == "("):
			self.match("(")
			value = self.parseExpression()
			self.match(")")
		else:
			value = self.parseNumber()
		return value

	def parseNumber(self):
		res = ""
		while self.cursor < len(self.input):
			if(isDigit(self.input[self.cursor])):
				res += self.freeMatch()
			else:
				break
		return int(res)
	
	def parseAddOp(self):
		token = self.peek()
		if(token == "+" or token == "-"):
			return self.freeMatch()
		else:
			return None

	def parseMultiOp(self):
		token = self.peek()
		if(token == "*" or token == "/"):
			return self.freeMatch()
		else: 
			return None

	# match current character
	def match(self, char):
		if self.input[self.cursor] == char:
			self.cursor += 1
			return char
		else:
			return None
	
	# free match current character
	def freeMatch(self):
		res = self.peek()
		self.cursor += 1
		return res

	def peek(self):
		return self.input[self.cursor]

	def consumeSpace(self):
		while self.cursor < len(self.input):
			if self.peek() == " ":
				self.freeMatch()
			else:
				break


def new_Parser(input):
	return Parser(input, 0)

def isDigit(input):
	return ord(input) >= 48 and ord(input) <= 57

def isMultiOp(char):
	return char == "*" or char == "/"

def isAddOp(char):
	return char == "+" or char == "-"
	

exp = "5-(1+2*3)-(4*5/2)+100"
parser = new_Parser(exp)


print(parser.parseExpression())


