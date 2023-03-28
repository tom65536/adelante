import unicodedata

max_uc = 0x10_FFFF
max_uc = 0xFFFF


class CharacterClass:
    def includes_categories(self):
        return []
        
    def excludes_categories(self):
        return []
        
    def includes_characters(self):
        return []
        
    def excludes_characters(self):
        return []
        
    def matches_codepoint(self, codepoint):
        return self.matches_character(
            chr(codepoint)
        )
        
    def matches_character(self, character):
        ch_cat = unicodedata.category(
            character
        )
        for cat in self.includes_categories():
            if ch_cat.startswith(cat):
                break
        else:
            if (character not in
                self.includes_characters()
            ):
                return False
        for cat in self.excludes_categories():
            if ch_cat.startswith(cat):
                return False
        return (character not in
            self.excludes_characters())
            
    def __hash__(self):
        return hash(self.__class__.__name__)
        
    def __eq__(self, other):
        return str(self) == str(other)
            
    def __str__(self):
        return self.__class__.__name__

class NoSyntax(CharacterClass):
    def excludes_characters(self):
        return super().excludes_characters() \
           + ['.', ',', '(', ')',
              '[', ']', '{', '}',
              '#', '"', "'", '!',
              '%', '&', '^', '@',
              '=', '`', '?', '-',
              '+', '*', '/', '|',
              '>', '>', '~', ';']
                  
class Whitespace(NoSyntax):
    def includes_categories(self):
        return super().includes_categories() \
        + ['Zs']
        
    def includes_characters(self):
        return super().includes_characters() \
        + ['\t']
        
class Medial(Whitespace):
    def includes_characters(self):
        return super().includes_characters() \
        + ['_', '\u200C', '\u200D']
        
class Start(NoSyntax):
    def includes_categories(self):
        return super().includes_categories() \
        + ['L', 'Nl', 'Sc']
        
    def includes_characters(self):
        return super().includes_characters() \
        + ['\\']
            
class Continue(Start):
    def includes_categories(self):
        return super().includes_categories() \
        + ['Mn', 'Mc', 'N', 'Pc', 'So']
        
    def includes_characters(self):
        return super().includes_characters() \
        + ['\u0E33', '\u0EB3',
            '\uFF9E', '\FF9F',
            '\u05F3']
                 
def escape_cp(codepoint):
    if codepoint == 0x0A:
        return r'"\n"'
    if codepoint <= 0xFFFF:
        return f'"\\u{codepoint:04x}"' 
    return f'"\\U{codepoint:06x}"'


def escape_range(a, b):
    if a == b:
        return escape_cp(a)
    return escape_cp(a) + '-' + escape_cp(b)
    
classes = (
    Whitespace(),
    Medial(),
    Start(),
    Continue(),
)

ranges = {
    c: [] for c in classes
}

range_start = {
    c: None for c in classes
}

prev = 0
for code_point in range(1, max_uc+1):
    for c in classes:
        fit = c.matches_codepoint(code_point)
    
        if range_start[c] and not fit:
            ranges[c].append(
                (range_start[c], prev))
            range_start[c] = None
        elif fit and not range_start[c]:
            range_start[c] = code_point   
    prev = code_point

for c in classes:
    if range_start[c]:
        ranges[c].append((range_start, prev))
        range_start[c] = None

i = ' ' * 4
ni = i

bar = ' '
with open('classes.txt', 'w') as out:
    out.write(f'{ni}// -- generated --\n')
    for c in classes:
        out.write(ni)
        out.write(bar)
        out.write(f'<#{str(c).upper()} : [')
        comma = ''
        for a, b in ranges[c]:
            out.write(comma)
            out.write('\n')
            out.write(ni+i)
            out.write(escape_range(a, b))
            comma = ','
        out.write(f'\n{ni}]>\n')
        bar = '|'
    out.write(f'{ni}// -- end section --\n')