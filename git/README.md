# Git hub

## Git 기본

### 1.1 GitHub란?

GitHub와 Git의 차이점은?
Git 과 GitHub는 완전히 다른 것으로,
Git은 Git리포지토리라고 불리는 데이터 저장소에 소스 코드 등을 넣어서 이용하는 것으로, 
이러한 Git리포지토리를 인터넷상에서 제공하는 서비스가 GitHub다.

- Git 리포지토리
- Organization
- Issue
- Wiki
- Pull Request

> - [GitHub 최근 트렌트살펴보기](http://github.com/trending)
> - [Git 참고](https://backlogtool.com/git-guide/kr/reference/remote.html)

### 1.2 버전관리란?

기본적으로 버전관리 시스템은 변경내역을 관리한다.
Git 이전에는 서브버전 등의 `집중형 버전관리` 시스템이 주류를 이루었지만
현재는 `분산형 버전 관리` 시스템 Git이 주류가 되었다.

#### 1.2.1 집중형과 분산형

**집중형이란?**

> 대표적인 집중형 버전관리 시스템으로는 서브비전이 있으며,
집중형은 말그대로 서버에 리포지토리를 집중시켜 배치한다.
따라서 하나의 소프트웨어를 개발할 때는 하나의 리포지토리만 존재한다.


**집중형 정리**

- 데이터가 중앙 서버에 집중되는 형태로, 덕분에 관리하기가 무척 단순하고 쉽다
- 서버에 접속할수 없거나 서버가 다운되는등 서버에 문제가 있을시 개발을 할 수 없다.
- 서버가 고장나서 데이터가 사라지면 끝...

*분산형이란?**

> Git이 대표적인 분산형 버전관리 시스템으로,
`Fork`라는 기능을 통해 특정 리포지토리를 자신의 계정으로 복제하여 사용가능하다.
이 복제된 리포지토리는 원본과는 완전히 다르며, 내마음대로 편집이 가능하다 (물론 라이센스는 논외로 치고)

**분산형 정리**

- 집중형과 달리 리포지토리가 여러 개 존재할 수 있으며, 그로인해 다소 복잡하다.
- 개인마다 리포지토리를 가질 수 있으므로 서버에 종속되지 않는다.(즉 서버 접속없이도 개발가능)
- github를 통하지 않아도 개발자끼리 리포지토토리를 직접 push,pull할경우 최신코드가 어디있는지 조차 알기 힘들 수 있다.

#### 1.2.2 그래서 집중형과 분산형중에 어떤 것이 좋은가?

집중형 ,분산형 모두 장점과 단점이 있으므로 경우에 따라 다르지만
Git과 GitHub의 보급에 따라 분산형 버전관리 시스템이 압도적으로 많이 사용되는 것은 분명하다.
또한 분산형의 단점중의 하나인 복잡성도 규칙을 만든다면 얼마든지 분산형으로도 집중형 버전관리 시스템을 구현할 수 있다.

> trending 섹션만 찾아봐도 요즈음 트렌드라 할만한 것들은 죄다 GitHub를 이용한다.
> `node의 npm` ,`freecodecampus` ,`ruby on rails`, `lalavel`등 뭐 가리지 않고 GitHub를 통해 서비스한다.
> 특히 요즘에는 국내 업체들도 GitHub를 쓰는 걸 꽤 찾아볼수 있다 (물론 아직까지는 유명한 대기업들..)
> 얼마전에 새로 개편한(2016-03 기준) 네이버 개발자센터의 새로운 js라이브러리고 `gitHub`와 `codepen`를통해 서비스~


### 1.3 설치
- 윈도우즈는 `mysysGit`
- 리눅스는 종류별로 패키지가 다르므로 찾아서 (우분투,CentOs등..에 맞게)
- 맥은 자동 설치되어있음 (업데이트정도만 해주자)

1. 컴포넌트는 기본설정 그대로
2. 환경변수 설정은 GitBash 사용(powershell이나 cmd는 사용법이 조금 달라서 너무 불편하다) 
   `Use Git Bash Only` 항목 선택
3. 개행코드 설정은 (운영체제별로)
   `윈도우는 CRLF 맥이나 리눅스는 LF`
4. 설치가 완료되면 bash창에서 `git`명령어로 확인


### 1.4 기본설정 및 사전준비

#### 사용자 이름과 메일 주소 설정
**가장 먼저 사용자 정보를 등록해줘야 한다.**
> 소스트리 사용시에도 이 설정을 안해주면 에러가 났었다.(소스트리내의 커맨드창에서 간단히 입력가능)

```bash
git config --global user.name "Fisrtname Lastname"
git config --global user.email "your_email@example.com"
```

#### 아래 명령어를 통해 설정파일 생성 확인
> 참고로 이때 설정한 이름과 메일주소로 Git Commit로그등에 사용되로 GitHub리포지토리를 공개할 경우에도 이때 설정한 정보가 사용된다.

```bash
~/.gitconfig 
```

#### 출력되는 명령어를 쉽게 읽을수 있도록 설정
```bash
git config --global color.ui auto
```

#### SSH Key 설정
> 아래 명령어로 sshkey 생성하면 `id_rsa` 라는 파일이 `비밀키`고, `id_rsa.pub`이 `공개키`이다
> 경로는 `bash`창에 출력되니 그거 보고 확인

```bash
ssh-keygen -t rsa -C "your_email@example.com"
...명령어..
Enter file in which to save the key (파일경로..):	//엔터키 입력
Enter passphrase (empty for no passphrase):		//비밀번호 입력
Enter....										//비밀번호 재 입력
```

#### GitHub에 공개키 등록
setting 창에 SSH key 메뉴에서 등록 Titlt은 키의 이름 key에는 `id_rsq.pub`의 내용을 복사해서 붙여넣자
`id_rsq.pub`의 내용은 아래 커맨드로 확인가능

```bash
cat ~/.ssh/id_rsa.pub
```
등록이 완료되면 공개 키 등록완료와 관련된 메일이 발송된다.
실제로 동작하는지 아래 코드로 확인
```bash
ssh -T git@github.com
```

#### 리포지토리 clone

Github사이트에서 생성한 레포지토리 주소를 복사해서 아래 명령어에 붙여 넣는다
```bash
git clone 리포지토리 주소
```

#### 코드를 stage에 추가 
commit을 하기 전에 스테이지에 파일을 등록해줘야 한다.
소스트리로 치면 체크박스 로 스테이징에 올리는 부분과 동일하다.
```bash
git add 파일명 or 폴더/
```

#### commit
```bash
git commit -m "커밋 메시지"
```

#### commit log 확인
아래 명령어로 commit 로그를 확인 할 수 있다.
```bash
git log
```

#### push
```bash
git push
```

### 1.5 기본적인 사용방법

#### 1) `git init` : 리포지토리 초기화
Git으로 버전관리를 하려면 리포지토리를 초기화해야 한다.
아래는 폴더를 생성하고 그 폴더내에서 리포지토리를 초기화 하는 예제다.
```bash
mkdir git-test
cd git-test
git init
```
> 초기화가 성공적으로 완료되면 `git init`명령어를 실행한 폴더이 `.git`이라는 이름의 폴더가 만들어진다.
> 이때 이 디렉토리 이하의 내용을 해당 리포지토리와 관련된 `working tree`라고 부르며, 이 안에서 파일준비, 등록된 파일 변경내역등을 관리하게 된다.

#### 2) `git status` : 리포지토리 상태확인
`git status`명령어는 git 리포지토리의 상태를 표시하는 명령어다.
리포지토리에 대응되는 조작을 하면 상태가 차례대로 변경되는데, 이러한 변경내역들을 확인할 수 있다.

```bash
git status
```

#### 3) `git add` : 스테이지 영역에 파일 추가
Git 리포지토리의 `working tree` 파일을 작성한 것 만으로는 Git 리포지토리의 버전관리 대응 시스템 파일이 등록되지 않는다.
즉 내가 `clone`한 경로에 새로운 파일을 추가 한후 ( 가령 README.md ) `git status`명령어를 실행해보면
추가한파일이 `Untracked files`로 출력되는걸 확인할 수 있다.
이렇게 변경(추가)된 파일을 Git 리포지토리에서 관리하도록 스테이지 영역에 등록하는 명령어
등록을 하면 `Untracked files` 가 `Changes to be committed`로 상태가 변경된걸 확인 할 수 있다.
> 소스트리 툴에서 체크박스로 스테이징에 변경(추가포함)된 파일을 스테이지에 등록하는 단계와 동일하다

```bash
touch README.md
git status
// README.md 파일이 Untracked files 로 되어있는 것 확인
git add README.md
git status
// Changes to be committed : 
//		new file : README.md
```

#### 4) `git commit` : 리포지토리 변경 내용을 기록
스테이지 영역에 기록된 시점의 파일들을 실제 리포지토리의 변경 내역에 반영하는 명령어
이 commit 기록을 기반으로 파일을 `working tree`에 복원하는 것이 가능하다.

```bash
git commit -m "First commit"
```


